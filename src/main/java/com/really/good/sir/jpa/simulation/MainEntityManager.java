package com.really.good.sir.jpa.simulation;

import java.util.HashMap;
import java.util.Map;

public class MainEntityManager {

    public static void main(String[] args) throws Exception {
        Map<String, String> properties = new HashMap<>();
        properties.put("fluffy.connection.url", "jdbc:mysql://localhost:3306/test_entity_manager");
        properties.put("fluffy.connection.username", "root");
        properties.put("fluffy.connection.password", "root");
        properties.put("fluffy.hbm2ddl.auto", "none");
        Configuration configuration = new Configuration(properties);
        configuration.addAnnotatedClass(Users.class);

        EntityManagerImpl em = new EntityManagerImpl(configuration);

        Users u = new Users("alicee3@gmail.com", "Alice");
        em.persist(u);

        System.out.println("Saved User: " + u); // should print generated ID

        em.close();
    }
}
