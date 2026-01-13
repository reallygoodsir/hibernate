package com.really.good.sir.jpa.annotations.full;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Insert data
        em.getTransaction().begin();
        Product p = new Product("Laptop");
        p.getRatings().put(Rating.GOOD, "5/5");
        p.getRatings().put(Rating.OK, "3/5");

        Order o1 = new Order(10);
        Order o2 = new Order(5);
        o1.setProduct(p); o2.setProduct(p);
        p.getOrders().add(o1); p.getOrders().add(o2);

        em.persist(p);
        em.getTransaction().commit();

        // EntityGraph fetch
        var graph = em.getEntityGraph("Product.withRatings");
        Product fetched = em.find(Product.class, p.getId(),
                java.util.Collections.singletonMap("javax.persistence.fetchgraph", graph));
        System.out.println("Ratings fetched with EntityGraph: " + fetched.getRatings());

        // Native query + ConstructorResult
        Object dto = em.createNativeQuery(
                "SELECT p.name, COUNT(o.id) AS total_orders " +
                        "FROM products p LEFT JOIN orders o ON p.id = o.product_id " +
                        "GROUP BY p.id",
                "ProductMapping"
        ).getSingleResult();
        System.out.println("ConstructorResult DTO: " + dto);

        em.close();
        emf.close();
    }
}
