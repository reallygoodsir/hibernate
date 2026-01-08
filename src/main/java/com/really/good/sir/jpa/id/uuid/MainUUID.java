package com.really.good.sir.jpa.id.uuid;

import javax.persistence.*;

public class MainUUID {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-uuid");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        UserUUID u1 = new UserUUID("Charlie", 28);
        UserUUID u2 = new UserUUID("Diana", 32);

        em.persist(u1);
        em.persist(u2);

        em.getTransaction().commit();

        System.out.println("User 1 UUID: " + u1.getId());
        System.out.println("User 2 UUID: " + u2.getId());

        em.close();
        emf.close();
    }
}
