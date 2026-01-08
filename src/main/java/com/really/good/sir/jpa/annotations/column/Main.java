package com.really.good.sir.jpa.annotations.column;

import javax.persistence.*;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mypu-column");
        EntityManager em = emf.createEntityManager();

        // Insert phase
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

        System.out.println("// ============================");
        System.out.println("// FETCHING USER FROM DATABASE");
        System.out.println("// ============================");

        // Fetch without initializing biography yet
        User loadedUser = em.find(User.class, id);

        System.out.println("Email: " + loadedUser.getEmail());
        System.out.println("First Name (EAGER): " + loadedUser.getFirstName());
        System.out.println("Last Name (DEFAULT EAGER): " + loadedUser.getLastName());

        System.out.println("// Biography is LAZY, Hibernate should fetch on access now");
        System.out.println("Biography (LAZY): " + loadedUser.getBiography());

        em.close();
        emf.close();
    }
}
