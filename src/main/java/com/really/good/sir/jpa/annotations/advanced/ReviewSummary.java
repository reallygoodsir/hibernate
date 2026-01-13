package com.really.good.sir.jpa.annotations.advanced;

public class ReviewSummary {
    private String reviewer;
    private int ratingCount;

    public ReviewSummary(String reviewer, int ratingCount) {
        this.reviewer = reviewer;
        this.ratingCount = ratingCount;
    }

    public String getReviewer() { return reviewer; }
    public int getRatingCount() { return ratingCount; }
}
