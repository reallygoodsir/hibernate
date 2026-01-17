package com.really.good.sir.jpa.annotations.temporal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        UserEvent event = new UserEvent(
                "alice",
                new Date(),
                "Temporary debug info"
        );

        em.getTransaction().begin();
        em.persist(event);
        em.getTransaction().commit();

        Long id = event.getId();
        em.clear();

        System.out.println("// ============================");
        System.out.println("// FETCHING USER EVENT FROM DATABASE");
        System.out.println("// ============================");

        UserEvent loadedEvent = em.find(UserEvent.class, id);

        System.out.println("ID: " + loadedEvent.getId());
        System.out.println("Username: " + loadedEvent.getUsername());
        System.out.println("Login Time: " + loadedEvent.getLoginTime());
        System.out.println("Debug Info (transient): " + loadedEvent.getDebugInfo());

        em.close();
        emf.close();
    }
}
