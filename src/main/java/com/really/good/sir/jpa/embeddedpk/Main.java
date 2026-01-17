package com.really.good.sir.jpa.embeddedpk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        OrderItemId id = new OrderItemId(1L, 100L);
        OrderItem item = new OrderItem(id, 2);

        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
