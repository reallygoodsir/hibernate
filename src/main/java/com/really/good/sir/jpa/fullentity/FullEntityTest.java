package com.really.good.sir.jpa.fullentity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FullEntityTest {

    public static void main(String[] args) {

        // Embedded object
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Kyiv");
        address.setCountry("Ukraine");

        // Related entity
        Category category = new Category();
        category.setName("Test Category");

        // Full entity
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

        // EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU-full-entity");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Persist category first
        em.persist(category);

        // Persist full entity
        em.persist(fullEntity);

        em.getTransaction().commit();

        System.out.println("Saved entity: " + fullEntity);

        em.close();
        emf.close();
    }
}
