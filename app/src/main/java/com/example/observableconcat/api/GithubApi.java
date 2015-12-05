package com.example.observableconcat.api;

import com.example.observableconcat.model.entity.Repository;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by frank on 5-12-2015.
 */
public interface GithubApi {
    @Headers("User-Agent: RxJava-Concat-Example")
    @GET("/users/square/repos?per_page=5")
    Observable<List<Repository>> getRepositories();
}
