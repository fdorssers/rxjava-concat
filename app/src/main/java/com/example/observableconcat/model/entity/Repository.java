package com.example.observableconcat.model.entity;

/**
 * Created by frank on 5-12-2015.
 */
public class Repository {
    public String name;

    public Repository() {

    }

    public Repository(RealmRepository realmRepository) {
        name = realmRepository.getName();
    }
}
