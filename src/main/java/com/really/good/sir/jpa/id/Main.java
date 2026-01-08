package com.really.good.sir.jpa.id;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU-sequence");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        User user = new User("john_doe");
        em.persist(user);
        em.getTransaction().commit();

        System.out.println("Generated ID = " + user.getId());

        em.close();
        emf.close();
    }
}
