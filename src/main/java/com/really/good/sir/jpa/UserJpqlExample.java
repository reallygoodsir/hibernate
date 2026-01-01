package com.really.good.sir.jpa;

import javax.persistence.*;
import java.util.List;

public class UserJpqlExample {

    public static void main(String[] args) {
        EntityManager em = Persistence
                .createEntityManagerFactory("myPU-xml")
                .createEntityManager();

        List<UserEntity> users = em.createQuery(
                        "SELECT u FROM UserEntity u WHERE u.userName = :name",
                        UserEntity.class
                )
                .setParameter("name", "Alice")
                .getResultList();

        users.forEach(u -> System.out.println(u.getEmail()));

        em.close();
    }
}
