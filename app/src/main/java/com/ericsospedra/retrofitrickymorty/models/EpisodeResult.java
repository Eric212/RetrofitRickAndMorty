package com.ericsospedra.retrofitrickymorty.models;

import java.util.List;

public class EpisodeResult extends Result<Episode>{
    public EpisodeResult(Info info, List<Episode> results) {
        super(info, results);
    }

    @Override
    public Info getInfo() {
        return null;
    }

    @Override
    public List<Episode> getResults() {
        return null;
    }
}
