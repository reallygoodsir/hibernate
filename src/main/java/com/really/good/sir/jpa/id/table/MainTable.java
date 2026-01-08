package com.really.good.sir.jpa.id.table;

import javax.persistence.*;

public class MainTable {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-table");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        UserTable u1 = new UserTable("Alice", 25);
        UserTable u2 = new UserTable("Bob", 30);

        em.persist(u1);
        em.persist(u2);

        em.getTransaction().commit();

        System.out.println("User 1 ID: " + u1.getId());
        System.out.println("User 2 ID: " + u2.getId());

        em.close();
        emf.close();
    }
}
