package com.really.good.sir.jpa.annotations.enums;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU-enum");
        EntityManager em = emf.createEntityManager();

        UserAccount user = new UserAccount(
                "alice",
                Status.PENDING,   // ORDINAL stored as 0
                Status.PENDING    // STRING stored as "PENDING"
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
