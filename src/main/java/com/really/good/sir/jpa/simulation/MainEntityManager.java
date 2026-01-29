package com.really.good.sir.jpa.simulation;

import java.util.HashMap;
import java.util.Map;

public class MainEntityManager {

    public static void main(String[] args) throws Exception {
        Map<String, String> properties = new HashMap<>();
        properties.put("fluffy.connection.url", "jdbc:mysql://localhost:3306/test_entity_manager");
        properties.put("fluffy.connection.username", "root");
        properties.put("fluffy.connection.password", "root");
        properties.put("fluffy.hbm2ddl.auto", "create");

        Configuration configuration = new Configuration(properties);
        configuration.addAnnotatedClass(User.class);

        EntityManagerImpl em = new EntityManagerImpl(configuration);

        System.out.println("=== PERSIST TEST ===");
        User u1 = new User("alice@gmail.com", "Alice");
        User u2 = new User("bob@gmail.com", "Bob");
        System.out.println("before persist 1");
        em.persist(u1);
        System.out.println("before persist 2");
        em.persist(u2);
        System.out.println("after persists");

        System.out.println("\n=== FIND TEST ===");
        User found1 = em.find(User.class, 1L);
        System.out.println("Found 1 => " + found1);
        User found2 = em.find(User.class, 2L);
        System.out.println("Found 2 => " + found2);

        System.out.println("\n=== MERGE TEST ===");
        found2.setName("Robert");
        User merged = em.merge(found2);
        System.out.println("Merged => " + merged);

        System.out.println("\n=== REMOVE TEST ===");
        em.remove(found1);
        User deletedCheck = em.find(User.class, 1L);
        System.out.println("After remove, find 1 => " + deletedCheck);

        System.out.println("\n=== RE-MERGE/DETACH TEST ===");
        // simulate detach by using a new instance with same id
        User detached = new User("bob@gmail.com", "Bobby");
        detached.setId(2L);
        User mergedDetached = em.merge(detached);
        System.out.println("Merged detached => " + mergedDetached);

        System.out.println("\n=== FLUSH TEST ===");
        em.flush(); // depending on your current impl

        System.out.println("\n=== CLOSE TEST ===");
        em.close();

        // optional negative test: should fail (expected)
        try {
            em.find(User.class, 2L);
        } catch (IllegalStateException ex) {
            System.out.println("Find after close correctly failed: " + ex.getMessage());
        }
    }
}
