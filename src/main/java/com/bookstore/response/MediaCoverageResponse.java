package com.bookstore.response;

import java.util.List;

public class MediaCoverageResponse {

    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public MediaCoverageResponse(List<String> results) {
        this.results = results;
    }
}
