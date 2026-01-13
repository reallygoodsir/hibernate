package com.really.good.sir.jpa.annotations.advanced;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "product_review")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewer;

    // Map with enum as key, String as value
    @ElementCollection
    @CollectionTable(
            name = "review_ratings",
            joinColumns = @JoinColumn(name = "review_id")
    )
    @MapKeyEnumerated(EnumType.STRING)  // store enum as string
    @Column(name = "rating")
    private Map<ReviewStatus, String> ratings = new HashMap<>();

    public ProductReview() {}

    public ProductReview(String reviewer) {
        this.reviewer = reviewer;
    }

    public Long getId() { return id; }
    public String getReviewer() { return reviewer; }
    public void setReviewer(String reviewer) { this.reviewer = reviewer; }

    public Map<ReviewStatus, String> getRatings() { return ratings; }
    public void setRatings(Map<ReviewStatus, String> ratings) { this.ratings = ratings; }
}
