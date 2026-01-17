package com.really.good.sir.jpa.id.uuid;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {

        EntityManager em = Configuration.getEntityManagerFactory().createEntityManager();

        em.getTransaction().begin();

        User u1 = new User("Charlie", 28);
        User u2 = new User("Diana", 32);

        em.persist(u1);
        em.persist(u2);

        em.getTransaction().commit();

        System.out.println("User 1 UUID: " + u1.getId());
        System.out.println("User 2 UUID: " + u2.getId());

        em.close();
    }
}
