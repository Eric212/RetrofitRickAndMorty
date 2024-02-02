package com.ericsospedra.retrofitrickymorty.models;

import java.util.List;

public class CharacterResult extends Result<Character>{
    public CharacterResult(Info info, List<Character> results) {
        super(info, results);
    }

    @Override
    public Info getInfo() {
        return super.info;
    }

    @Override
    public List<Character> getResults() {
        return results;
    }
}
