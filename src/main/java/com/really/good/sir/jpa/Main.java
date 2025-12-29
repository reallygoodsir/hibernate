package com.really.good.sir.jpa;

import javax.persistence.*;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-xml");
//              Persistence.createEntityManagerFactory("myPU");

        EntityManager em = emf.createEntityManager();

        // INSERT
        em.getTransaction().begin();

        UserEntity user = new UserEntity("Alice", "alice@gmail.com");
        em.persist(user);

        em.getTransaction().commit();

        Long userId = user.getId();

        // SELECT
        UserEntity foundUser = em.find(UserEntity.class, userId);
        System.out.println(foundUser.getUserName());

        em.close();
        emf.close();
    }
}
