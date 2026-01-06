package com.really.good.sir.jpa.advancedmappings.singletable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("pu-single-table");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Car car = new Car();
        car.setSpeed(180);
        car.setDoors(4);

        Bike bike = new Bike();
        bike.setSpeed(40);
        bike.setHasPedals(true);

        em.persist(car);
        em.persist(bike);

        em.getTransaction().commit();

        em.close();
        emf.close();

    }
}
