package com.really.good.sir.jpa.relationships.manytomany;

import javax.persistence.*;
import java.util.Arrays;

public class ManyToManyDemo {

    public static void main(String[] args) {
//        save();
//        saveStudent();
//        saveCourse();
//        readStudentWithCourses(2L);
//        readCourseWithStudents(1L);
//        update(1L);
//        update(1L, 2L);
//        remove(1L, 3L);
    }

    public static void save() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = new Student();

        Course c1 = new Course();
        Course c2 = new Course();

        student.setCourses(Arrays.asList(c1, c2));

        em.persist(c1);
        em.persist(c2);
        em.persist(student);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }


    public static void saveStudent() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = new Student();
        em.persist(student);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void saveCourse() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Course course = new Course();
        em.persist(course);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void readStudentWithCourses(Long studentId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        Student student =
                em.createQuery(
                                "SELECT s FROM Student s " +
                                        "LEFT JOIN FETCH s.courses " +
                                        "WHERE s.id = :id",
                                Student.class
                        )
                        .setParameter("id", studentId)
                        .getSingleResult();

        System.out.println("Student " + student.getId());
        for (Course c : student.getCourses()) {
            System.out.println("   Course " + c.getId());
        }

        em.close();
        emf.close();
    }

    public static void readCourseWithStudents(Long courseId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        Course course =
                em.createQuery(
                                "SELECT c FROM Course c " +
                                        "LEFT JOIN FETCH c.students " +
                                        "WHERE c.id = :id",
                                Course.class
                        )
                        .setParameter("id", courseId)
                        .getSingleResult();

        System.out.println("Course " + course.getId());
        for (Student s : course.getStudents()) {
            System.out.println("   Student " + s.getId());
        }

        em.close();
        emf.close();
    }

    public static void update(Long studentId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = em.find(Student.class, studentId);

        Course newCourse = new Course();
        em.persist(newCourse);

        student.getCourses().add(newCourse);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void update(Long studentId, Long courseId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = em.find(Student.class, studentId);
        Course course = em.find(Course.class, courseId);

        student.getCourses().add(course);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void remove(Long studentId, Long courseId) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("myPU-relationships");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = em.find(Student.class, studentId);
        Course course = em.find(Course.class, courseId);

        student.getCourses().remove(course);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }


}

