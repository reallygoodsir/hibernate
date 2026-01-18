package com.really.good.sir.jpa.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProxyMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // 1. Insert two entities
        em.getTransaction().begin();
        Biography bio = new Biography("Alice is a backend engineer who likes Hibernate internals.");
        User user = new User("alice@example.com", bio);
        em.persist(bio);
        em.persist(user);
        em.getTransaction().commit();

        em.clear();

        // 2. Load User only
        User u = em.find(User.class, user.getId());

        // 3. Access lazy association: still proxy
        Biography b = u.getBiography();

        System.out.println("STEP 1 — proxy class: " + b.getClass().getName());
        System.out.println("Is proxy initialized? " + org.hibernate.Hibernate.isInitialized(b));

        // 4. Trigger lazy load
        String text = b.getText(); // SQL issued here

        System.out.println("STEP 2 — after accessing text:");
        System.out.println("Is proxy initialized? " + org.hibernate.Hibernate.isInitialized(b));
        System.out.println("Biography text: " + text);

        em.close();
        emf.close();
    }
}
