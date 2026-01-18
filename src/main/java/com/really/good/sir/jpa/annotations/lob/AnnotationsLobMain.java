package com.really.good.sir.jpa.annotations.lob;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.InputStream;

public class AnnotationsLobMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        byte[] imageBytes;
        try (InputStream is = AnnotationsLobMain.class.getClassLoader().getResourceAsStream("jpeg-home.jpg")) {
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
        Document doc = new Document("My Doc with Image", largeText, imageBytes);

        em.getTransaction().begin();
        em.persist(doc);
        em.getTransaction().commit();

        Long id = doc.getId();
        em.clear();

        Document loaded = em.find(Document.class, id);

        System.out.println("ID: " + loaded.getId());
        System.out.println("Name: " + loaded.getName());
        System.out.println("Text content length (CLOB): " + loaded.getTextContent().length());
        System.out.println("Binary content length (BLOB): " + loaded.getBinaryContent().length);

        em.close();
        emf.close();
    }
}
