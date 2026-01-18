package com.really.good.sir.jpa.cache;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;

public class Configuration {

    public static EntityManagerFactory createEntityManagerFactory() {
        var registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/jpa_cache?useSSL=false")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")

                // L2 cache
                .applySetting("hibernate.cache.use_second_level_cache", "true")
                .applySetting("hibernate.cache.use_query_cache", "true")
                .applySetting("hibernate.cache.region.factory_class", "org.hibernate.cache.internal.NoCachingRegionFactory")
                .build();

        var metadata = new MetadataSources(registry)
                .addAnnotatedClass(Product.class)
                .buildMetadata();

        SessionFactory sessionFactory = metadata.buildSessionFactory();
        return sessionFactory.unwrap(EntityManagerFactory.class);
    }
}
