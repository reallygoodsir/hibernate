package com.really.good.sir.jpa.annotations.advanced;

import javax.persistence.*;

@Entity
@NamedEntityGraph(
        name = "ProductReview.withRatings",
        attributeNodes = @NamedAttributeNode("ratings")
)
public class ProductReviewGraph extends ProductReview {
    // empty subclass to demonstrate EntityGraph
}
