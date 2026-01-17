package com.really.good.sir.jpa.id.uuid;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;

public class Configuration {

    public static EntityManagerFactory getEntityManagerFactory() {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/jpa_uuid")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .build();

        MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClass(User.class);

        SessionFactory sessionFactory = sources.buildMetadata().buildSessionFactory();
        return sessionFactory.unwrap(EntityManagerFactory.class);
    }
}
