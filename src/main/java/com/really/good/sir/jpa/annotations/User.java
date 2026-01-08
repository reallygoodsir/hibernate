package com.really.good.sir.jpa.annotations;

import javax.persistence.*;

@Entity
@Table(name = "users",
        schema = "jpa_annotations_example"
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "uk_name",
//                        columnNames = {"name"}
//                )
//        }
        )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int age;

    public User() {
    }  // JPA requires no-arg constructor

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
