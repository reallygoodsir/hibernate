package com.really.good.sir.jpa.annotations.column;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;

public class Configuration {

    public static EntityManagerFactory createEntityManagerFactory() {
        // Build the Hibernate service registry
        var registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/jpa_column_basic")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")

                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                .applySetting("hibernate.hbm2ddl.auto", "create")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")

                // optional
                .applySetting("hibernate.jdbc.time_zone", "UTC")
                .build();

        // Add annotated classes
        var metadata = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata();

        // Build SessionFactory (Hibernate)
        SessionFactory sessionFactory = metadata.buildSessionFactory();

        // Unwrap JPA EntityManagerFactory
        EntityManagerFactory emf = sessionFactory.unwrap(EntityManagerFactory.class);
        return emf;
    }
}
