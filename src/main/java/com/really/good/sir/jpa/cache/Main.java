package com.really.good.sir.jpa.cache;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Configuration.createEntityManagerFactory();

        EntityManager em1 = emf.createEntityManager();
        em1.getTransaction().begin();

        Product p1 = new Product("Laptop", 1999.99);
        em1.persist(p1);

        em1.getTransaction().commit();
        em1.close();

        // Load from cache
        EntityManager em2 = emf.createEntityManager();
        Product cachedProduct = em2.find(Product.class, p1.getId());
        System.out.println("Loaded Product: " + cachedProduct.getName() + " $" + cachedProduct.getPrice());
        em2.close();

        emf.close();
    }
}
