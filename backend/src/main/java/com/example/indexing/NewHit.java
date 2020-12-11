package com.example.indexing;

import org.elasticsearch.search.SearchHit;

public class NewHit {
    private SearchHit hit;
    private float newScore;

    public SearchHit getHit() {
        return hit;
    }

    public void setHit(SearchHit hit) {
        this.hit = hit;
    }

    public float getNewScore() {
        return newScore;
    }

    public void setNewScore(float newScore) {
        this.newScore = newScore;
    }

    public NewHit(SearchHit hit, float newScore) {
        this.hit = hit;
        this.newScore = newScore;
    }
}
