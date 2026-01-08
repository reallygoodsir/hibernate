package com.really.good.sir.jpa.customseq;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "user_seq_gen")
    @GenericGenerator(
            name = "user_seq_gen",
            strategy = "com.really.good.sir.jpa.customseq.CustomSequenceGenerator"
    )
    private Long id;

    private String name;
    private int age;

    public User() {}
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() { return id; }
}
