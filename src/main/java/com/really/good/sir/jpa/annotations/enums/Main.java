package com.really.good.sir.jpa.annotations.enums;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        UserAccount user = new UserAccount(
                "alice",
                Status.PENDING,
                Status.PENDING
        );

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        Long id = user.getId();
        em.clear();

        UserAccount loaded = em.find(UserAccount.class, id);

        System.out.println("ID: " + loaded.getId());
        System.out.println("Username: " + loaded.getUsername());
        System.out.println("Status ORDINAL: " + loaded.getStatusOrdinal());
        System.out.println("Status STRING: " + loaded.getStatusString());

        em.close();
        emf.close();
    }
}
