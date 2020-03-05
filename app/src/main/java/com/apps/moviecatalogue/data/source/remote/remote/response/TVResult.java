package com.apps.moviecatalogue.data.source.remote.remote.response;

import java.util.ArrayList;

public class TVResult {
    public ArrayList<TVResponse> getResults() {
        return results;
    }

    public void setResults(ArrayList<TVResponse> results) {
        this.results = results;
    }

    private ArrayList<TVResponse> results;
}
