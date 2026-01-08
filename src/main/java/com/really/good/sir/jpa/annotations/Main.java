package com.really.good.sir.jpa.annotations;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU-annotations");
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
