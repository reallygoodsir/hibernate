package com.really.good.sir.jpa.fullentity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FullEntityMain {

    public static void main(String[] args) {

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Kyiv");
        address.setCountry("Ukraine");

        Category category = new Category();
        category.setName("Test Category");

        FullEntity fullEntity = new FullEntity();
        fullEntity.setName("John Doe");
        fullEntity.setAge(30);
        fullEntity.setScore(95);
        fullEntity.setBigNumber(123456789L);
        fullEntity.setLongNumber(987654321L);
        fullEntity.setSalary(4500.50);
        fullEntity.setRating(4.9);
        fullEntity.setActive(true);
        fullEntity.setVerified(true);
        fullEntity.setBalance(new BigDecimal("12345.67"));
        fullEntity.setBirthDate(new Date());
        fullEntity.setCreatedAt(new Date());
        fullEntity.setLocalDate(LocalDate.now());
        fullEntity.setLocalDateTime(LocalDateTime.now());
        fullEntity.setStatus(FullEntity.Status.IN_PROGRESS);
        fullEntity.setDescription("This is a large text description.");
        fullEntity.setDocument(new byte[]{1, 2, 3, 4, 5});
        fullEntity.setAddress(address);
        fullEntity.setCategory(category);

        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(category);
        em.persist(fullEntity);
        em.getTransaction().commit();

        System.out.println("Saved entity: " + fullEntity);

        em.close();
        emf.close();
    }
}
