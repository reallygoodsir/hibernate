package com.really.good.sir.jpa.annotations.column;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        // Normal JPA EntityManager
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        User user = new User(
                "alice@example.com",
                "Alice is a software engineer who enjoys writing detailed documentation.",
                "Alice",
                "Johnson"
        );
        em.persist(user);
        em.getTransaction().commit();

        Long id = user.getId();
        em.clear();

        System.out.println(">>> Fetching user via JPA");
        User loaded = em.find(User.class, id);

        System.out.println("Email: " + loaded.getEmail());
        System.out.println("First Name: " + loaded.getFirstName());
        System.out.println("Biography (lazy): " + loaded.getBiography());

        em.close();
        emf.close();
    }
}
