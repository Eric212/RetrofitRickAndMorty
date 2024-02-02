package com.ericsospedra.retrofitrickymorty.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public abstract class Result<T> {
    Info info;
    List<T> results;

    public Result(Info info, List<T> results) {
        this.info = info;
        this.results = results;
    }

    public abstract Info getInfo();

    public abstract List<T> getResults();
}
