package com.really.good.sir.jpa.relationships.onetoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OneToOneDemo {

    public static void main(String[] args) {
//        save2();

//        Credential credential = readCredential();
//        System.out.println(credential.getUser());

//        removeCredential();
//        update();
//        updateWithCascadeMerge();

//        removeCredential2();
    }

    private static void save() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* One-to-One */
        User user = new User();
        user.setType("typeOld");
        Credential credential = new Credential();
        credential.setName("nameOld");
        user.setCredential(credential);
        credential.setUser(user);

        em.persist(user);
        em.persist(credential);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void save2() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* One-to-One */
        User user = new User();
        user.setType("typeOld");
        Credential credential = new Credential();
        credential.setName("nameOld");
        user.setCredential(credential);
        credential.setUser(user);

        em.persist(credential);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static Credential readCredential() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");

        EntityManager em = emf.createEntityManager();

        Credential credential = em.find(Credential.class, 1L);
        System.out.println();
        System.out.println();

        em.close();
        emf.close();

        return credential;
    }

    private static void removeCredential() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Credential credential = em.find(Credential.class, 4L);
        em.remove(credential);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

//    private static void removeCredential2() {
//        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("myPU-relationships");
//
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Credential credential = new Credential();
//        credential.setId(4L);
//        em.remove(credential);
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
//    }


    private static void update() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Credential credential = em.find(Credential.class, 4L);
        credential.setName("nameNew2");
        User user = credential.getUser();
        user.setType("typeNew2");

        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    private static void updateWithCascadeMerge() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Credential credential = em.find(Credential.class, 4L);
        credential.setName("nameNew7");
        credential.getUser().setType("typeNew7");

        em.merge(credential);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }


}
