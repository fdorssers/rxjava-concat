package com.example.observableconcat.model;

import com.example.observableconcat.model.entity.Repository;

import java.util.List;

import rx.Observable;

/**
 * Created by frank on 5-12-2015.
 */
public interface RepoModel {
    Observable<List<Repository>> getRepositories();
}
