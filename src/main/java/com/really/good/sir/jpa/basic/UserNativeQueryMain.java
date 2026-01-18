package com.really.good.sir.jpa.basic;

import javax.persistence.*;
import java.util.List;

public class UserNativeQueryMain {

    public static void main(String[] args) {
        EntityManager em = Configuration.createEntityManagerFactory().createEntityManager();

        List<UserEntity> users = em.createNativeQuery(
                        "SELECT * FROM users WHERE email = ?",
                        UserEntity.class
                )
                .setParameter(1, "alice@gmail.com")
                .getResultList();

        users.forEach(u -> System.out.println(u.getUserName()));

        em.close();
    }
}
