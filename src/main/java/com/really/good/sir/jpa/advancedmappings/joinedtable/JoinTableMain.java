package com.really.good.sir.jpa.advancedmappings.joinedtable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JoinTableMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Car car = new Car();
        car.setSpeed(200);
        car.setDoors(2);

        Bike bike = new Bike();
        bike.setSpeed(35);
        bike.setHasPedals(true);

        em.persist(car);
        em.persist(bike);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
