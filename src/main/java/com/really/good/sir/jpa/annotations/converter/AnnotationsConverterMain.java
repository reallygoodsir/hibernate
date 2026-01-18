package com.really.good.sir.jpa.annotations.converter;

import javax.persistence.EntityManager;

public class AnnotationsConverterMain {
    public static void main(String[] args) {

        var emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // INSERT
        em.getTransaction().begin();
        User u = new User("active");
        em.persist(u);
        em.getTransaction().commit();

                // LOAD (PostLoad fires)
        User loaded = em.find(User.class, u.getId());

        // UPDATE
        em.getTransaction().begin();
        loaded.setStatus("inactive");
        em.getTransaction().commit();

        // REMOVE
        em.getTransaction().begin();
        em.remove(loaded);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
