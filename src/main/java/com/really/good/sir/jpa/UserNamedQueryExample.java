package com.really.good.sir.jpa;

import javax.persistence.*;
import java.util.List;

public class UserNamedQueryExample {

    public static void main(String[] args) {
        EntityManager em = Persistence
                .createEntityManagerFactory("myPU-xml")
                .createEntityManager();

        List<UserEntity> users = em
                .createNamedQuery("User.findByEmail", UserEntity.class)
                .setParameter("email", "alice@gmail.com")
                .getResultList();

        users.forEach(u -> System.out.println(u.getUserName()));

        em.close();
    }
}
