package com.really.good.sir.jpa.annotations.id;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        User user = new User("Peter", 33);
        em.persist(user);

        em.getTransaction().commit();

        System.out.println("User saved with ID: " + user.getId());

        em.close();
        emf.close();
    }
}
