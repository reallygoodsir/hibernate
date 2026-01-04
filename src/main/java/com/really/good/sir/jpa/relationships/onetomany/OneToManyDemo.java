package com.really.good.sir.jpa.relationships.onetomany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class OneToManyDemo {
    public static void main(String[] args) {
//        save2();
//        update(9L);
        update2(10L);
//        read();
//        remove(8L);

        //       remove2(9L);
    }

    public static void save() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* One-To-Many */
        Customer customer = new Customer();
        em.persist(customer);

        Order o1 = new Order();
        o1.setCustomer(customer);

        Order o2 = new Order();
        o2.setCustomer(customer);

        em.persist(o1);
        em.persist(o2);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }


    public static void save2() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* One-To-Many */
        Customer customer = new Customer();
        customer.setName("c1");
        Order o1 = new Order();
        o1.setName("o1");
        o1.setCustomer(customer);

        Order o2 = new Order();
        o2.setName("o2");
        o2.setCustomer(customer);

        customer.setOrders(Arrays.asList(o1, o2));

        em.persist(customer);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void update(Long customerId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Update customer name
        em.createQuery(
                        "UPDATE Customer c " +
                                "SET c.name = CONCAT(c.name, 'New') " +
                                "WHERE c.id = :id"
                )
                .setParameter("id", customerId)
                .executeUpdate();

        // Update all orders of that customer
        em.createQuery(
                        "UPDATE Order o " +
                                "SET o.name = CONCAT(o.name, 'New') " +
                                "WHERE o.customer.id = :id"
                )
                .setParameter("id", customerId)
                .executeUpdate();

        em.getTransaction().commit();

        em.clear();
        em.close();
        emf.close();
    }

    public static void update2(Long customerId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, customerId);
        customer.setName("N123");
        List<Order> orders = customer.getOrders();
        for (Order order : orders) {
            order.setName("O987");
        }
        em.merge(customer);

        em.getTransaction().commit();

        em.clear();
        em.close();
        emf.close();
    }


    public static void read() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        List<Customer> customers =
                em.createQuery(
                        "SELECT DISTINCT c " +
                                "FROM Customer c " +
                                "LEFT JOIN FETCH c.orders",
                        Customer.class
                ).getResultList();

        for (Customer c : customers) {
            System.out.println("Customer: " + c.getId() + " - " + c.getName());

            if (c.getOrders() != null) {
                for (Order o : c.getOrders()) {
                    System.out.println("   Order: " + o.getId() + " - " + o.getName());
                }
            }
        }

        em.close();
        emf.close();
    }

    public static void remove(Long customerId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Remove orders first (owning side)
        em.createQuery(
                        "DELETE FROM Order o WHERE o.customer.id = :id"
                )
                .setParameter("id", customerId)
                .executeUpdate();

        // Remove customer
        em.createQuery(
                        "DELETE FROM Customer c WHERE c.id = :id"
                )
                .setParameter("id", customerId)
                .executeUpdate();

        em.getTransaction().commit();

        em.clear();
        em.close();
        emf.close();
    }

    public static void remove2(Long customerId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, customerId);
        em.remove(customer);

        em.getTransaction().commit();

        em.clear();
        em.close();
        emf.close();
    }

    // Method showing N+1
    public static void readNPlusOne() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        // 1 query
        List<Customer> customers =
                em.createQuery(
                        "SELECT c FROM Customer c",
                        Customer.class
                ).getResultList();

        for (Customer c : customers) {
            System.out.println("Customer: " + c.getId() + " - " + c.getName());

            // +1 query PER customer
            for (Order o : c.getOrders()) {
                System.out.println("   Order: " + o.getId() + " - " + o.getName());
            }
        }

        em.close();
        emf.close();
    }

}
