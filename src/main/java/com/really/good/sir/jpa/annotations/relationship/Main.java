package com.really.good.sir.jpa.annotations.relationship;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {

        var emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Create user
        UserAccount user = new UserAccount();
        user.setUsername("john_doe");
        em.persist(user);

        // Profile
        UserProfile profile = new UserProfile();
        profile.setFullName("John Doe");
        user.setProfile(profile);
        em.persist(profile);

        // Tags
        Tag urgent = new Tag();
        urgent.setLabel("urgent");
        em.persist(urgent);

        Tag home = new Tag();
        home.setLabel("home");
        em.persist(home);

        // Order
        UserOrder order = new UserOrder();
        order.setOrderNumber(1001);
        order.setUserAccount(user);
        order.getTags().add(urgent);
        order.getTags().add(home);
        em.persist(order);

        em.getTransaction().commit();

        System.out.println("Relations example inserted successfully.");

        em.close();
        emf.close();
    }
}
