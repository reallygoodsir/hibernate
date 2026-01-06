package com.really.good.sir.jpa.advancedmappings.tableperclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("pu-table-per-class");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Car car = new Car();
        car.setSpeed(220);
        car.setDoors(2);

        Bike bike = new Bike();
        bike.setSpeed(30);
        bike.setHasPedals(true);

        em.persist(car);
        em.persist(bike);

        em.getTransaction().commit();

        em.close();
        emf.close();

    }
}
