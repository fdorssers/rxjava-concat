package com.example.observableconcat.model;

import com.example.observableconcat.api.GithubApi;
import com.example.observableconcat.model.entity.RealmRepository;
import com.example.observableconcat.model.entity.Repository;
import com.example.observableconcat.rx.RealmObservable;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import rx.Observable;

/**
 * Created by frank on 5-12-2015.
 */
public class RepoModelImpl implements RepoModel {

    GithubApi githubApi;

    public RepoModelImpl() {
        githubApi = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .setClient(new OkClient(new OkHttpClient()))
                .build().create(GithubApi.class);
    }

    @Override
    public Observable<List<Repository>> getRepositories() {
        Observable<List<Repository>> local = RealmObservable.results(realm -> realm.where(RealmRepository.class).findAll())
                .map(this::convertFromRealm);

        Observable<List<Repository>> remote = githubApi.getRepositories()
                .doOnNext(this::storeInRealm);

        return Observable.concat(local, remote);
    }

    private List<Repository> convertFromRealm(RealmResults<RealmRepository> realmRepositories) {
        List<Repository> repositories = new ArrayList<>();
        for(RealmRepository realmRepository : realmRepositories) {
            repositories.add(new Repository(realmRepository));
        }
        return repositories;
    }

    private void storeInRealm(List<Repository> repositories) {
        List<RealmRepository> realmRepositories = new ArrayList<>();
        for(Repository repository : repositories) {
            realmRepositories.add(new RealmRepository(repository));
        }

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.allObjects(RealmRepository.class).clear();
        realm.copyToRealm(realmRepositories);
        realm.commitTransaction();
    }
}
