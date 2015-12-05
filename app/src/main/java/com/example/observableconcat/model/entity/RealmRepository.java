package com.example.observableconcat.model.entity;

import io.realm.RealmObject;

/**
 * Created by frank on 5-12-2015.
 */
public class RealmRepository extends RealmObject {
    private String name;

    public RealmRepository() {

    }

    public RealmRepository(Repository repository) {
        name = repository.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
