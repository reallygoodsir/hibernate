package com.really.good.sir.jpa.customseq;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU-custom-seq");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        User u1 = new User("Alice", 25);
        User u2 = new User("Bob", 30);
        User u3 = new User("Charlie", 28);

        em.persist(u1);
        em.persist(u2);
        em.persist(u3);

        em.getTransaction().commit();

        System.out.println(u1.getId());
        System.out.println(u2.getId());
        System.out.println(u3.getId());

        em.close();
        emf.close();
    }
}
