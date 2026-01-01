package com.really.good.sir.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-xml");

        EntityManager em = emf.createEntityManager();

        /* =========================
           INSERT (2 users)
           ========================= */
        em.getTransaction().begin();

        UserEntity user1 = new UserEntity("Alice", "alice@gmail.com");
        UserEntity user2 = new UserEntity("Bob", "bob@gmail.com");

        em.persist(user1);
        em.persist(user2);

        em.getTransaction().commit();

        Long user1Id = user1.getId();
        Long user2Id = user2.getId();

        /* =========================
           SELECT (both users)
           ========================= */
        UserEntity foundUser1 = em.find(UserEntity.class, user1Id);
        UserEntity foundUser2 = em.find(UserEntity.class, user2Id);

        System.out.println("Found user 1: " + foundUser1.getUserName());
        System.out.println("Found user 2: " + foundUser2.getUserName());

        /* =========================
           UPDATE (first user)
           ========================= */
        em.getTransaction().begin();

        foundUser1.setUserName("Alice Updated");
        foundUser1.setEmail("alice.updated@gmail.com");

        em.getTransaction().commit();

        /* =========================
           SELECT (first user again)
           ========================= */
        UserEntity updatedUser1 = em.find(UserEntity.class, user1Id);
        System.out.println("Updated user 1: " + updatedUser1.getUserName());

        /* =========================
           DELETE (second user)
           ========================= */
        em.getTransaction().begin();

        UserEntity userToDelete = em.find(UserEntity.class, user2Id);
        em.remove(userToDelete);

        em.getTransaction().commit();

        System.out.println("User 2 deleted");

        em.close();
        emf.close();
    }
}
