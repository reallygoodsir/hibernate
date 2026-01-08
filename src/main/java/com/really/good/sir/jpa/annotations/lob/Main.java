package com.really.good.sir.jpa.annotations.lob;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU-lob");
        EntityManager em = emf.createEntityManager();

        // Load JPEG from resources
        byte[] imageBytes;
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("jpeg-home.jpg")) {
            if (is == null) {
                System.out.println("File not found in resources!");
                return;
            }
            imageBytes = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String largeText = "This is a very large text content. ".repeat(100);

        // Create entity
        Document doc = new Document("My Doc with Image", largeText, imageBytes);

        // Persist
        em.getTransaction().begin();
        em.persist(doc);
        em.getTransaction().commit();

        Long id = doc.getId();
        em.clear();

        // Fetch from DB
        Document loaded = em.find(Document.class, id);

        System.out.println("ID: " + loaded.getId());
        System.out.println("Name: " + loaded.getName());
        System.out.println("Text content length (CLOB): " + loaded.getTextContent().length());
        System.out.println("Binary content length (BLOB): " + loaded.getBinaryContent().length);

        em.close();
        emf.close();
    }
}
