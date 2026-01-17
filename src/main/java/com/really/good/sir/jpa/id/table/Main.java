package com.really.good.sir.jpa.id.table;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        User u1 = new User("Alice", 25);
        User u2 = new User("Bob", 30);

        em.persist(u1);
        em.persist(u2);

        em.getTransaction().commit();

        System.out.println("User 1 ID: " + u1.getId());
        System.out.println("User 2 ID: " + u2.getId());

        em.close();
        emf.close();
    }
}
