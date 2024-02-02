package com.ericsospedra.retrofitrickymorty.models;

import java.util.List;

public class LocationResult extends Result<Location>{
    public LocationResult(Info info, List<Location> results) {
        super(info, results);
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public List<Location> getResults() {
        return results;
    }
}
