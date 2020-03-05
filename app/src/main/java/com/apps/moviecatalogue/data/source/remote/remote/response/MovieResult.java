package com.apps.moviecatalogue.data.source.remote.remote.response;

import java.util.ArrayList;

public class MovieResult {
    public ArrayList<MovieResponse> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieResponse> results) {
        this.results = results;
    }

    private ArrayList<MovieResponse> results;
}
