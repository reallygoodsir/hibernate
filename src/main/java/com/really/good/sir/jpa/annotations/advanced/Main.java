package com.really.good.sir.jpa.annotations.advanced;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Insert example
        em.getTransaction().begin();
        ProductReview review = new ProductReview("Alice");
        review.getRatings().put(ReviewStatus.APPROVED, "5/5");
        review.getRatings().put(ReviewStatus.PENDING, "4/5");
        em.persist(review);
        em.getTransaction().commit();

        // Use SQLResultSetMapping + ConstructorResult
        Object summary = em.createNativeQuery(
                "SELECT reviewer, COUNT(*) AS rating_count FROM product_review pr " +
                        "JOIN review_ratings rr ON pr.id = rr.review_id " +
                        "GROUP BY reviewer",
                "ReviewSummaryMapping"
        ).getSingleResult();
        System.out.println("ConstructorResult mapping: " + summary);

        // Use EntityGraph to fetch ratings eagerly
        var graph = em.getEntityGraph("ProductReview.withRatings");
        ProductReview fetched = em.find(ProductReview.class, review.getId(),
                java.util.Collections.singletonMap("javax.persistence.fetchgraph", graph));
        System.out.println("EntityGraph fetched ratings: " + fetched.getRatings());

        em.close();
        emf.close();
    }
}
