package com.really.good.sir.jpa.customseq;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;

public class Configuration {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/jpa_custom_seq")
                    .applySetting("hibernate.connection.username", "root")
                    .applySetting("hibernate.connection.password", "root")
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .applySetting("hibernate.show_sql", "true")
                    .applySetting("hibernate.format_sql", "true")
                    .applySetting("hibernate.hbm2ddl.auto", "update")
                    .build();

            SessionFactory sf = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .buildMetadata()
                    .buildSessionFactory();

            emf = sf.unwrap(EntityManagerFactory.class);
        }
        return emf;
    }

    public static void shutdown() {
        if (emf != null) {
            emf.close();
        }
    }
}
