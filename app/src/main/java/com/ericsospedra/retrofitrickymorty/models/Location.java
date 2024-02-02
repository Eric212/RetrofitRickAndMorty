package com.ericsospedra.retrofitrickymorty.models;

import java.util.List;

public class Location {
    private int id;
    private String name;
    private String type;
    private String dimension;
    private List<String> residents;
    private String url;

    public Location(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Location(int id, String name, String type, String dimension, List<String> residents) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.dimension = dimension;
        this.residents = residents;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDimension() {
        return dimension;
    }

    public List<String> getResidents() {
        return residents;
    }
}
