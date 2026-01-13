package com.really.good.sir.jpa.annotations.queries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Insert a product
        em.getTransaction().begin();
        Product p = new Product();
        p.setName("Laptop");
        p.setCategory("Electronics");
        p.getAttributes().put("RAM", "16GB");
        p.getAttributes().put("CPU", "Intel i7");
        em.persist(p);
        em.getTransaction().commit();

        // NamedQuery example
        Product result = em.createNamedQuery("Product.findByCategory", Product.class)
                .setParameter("cat", "Electronics")
                .getSingleResult();
        System.out.println("NamedQuery result: " + result.getName());

        // Native query example
        Product nativeResult = em.createNamedQuery("Product.nativeFindAll", Product.class)
                .getSingleResult();
        System.out.println("NativeQuery result: " + nativeResult.getName());

        // SQL result set mapping example
        Object nameOnly = em.createNativeQuery(
                "SELECT name FROM product",
                "ProductNameMapping"
        ).getSingleResult();
        System.out.println("SQLResultSetMapping result: " + nameOnly);

        em.close();
        emf.close();
    }
}
