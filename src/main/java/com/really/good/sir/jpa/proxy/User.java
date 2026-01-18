package com.really.good.sir.jpa.proxy;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biography_id")
    private Biography biography;

    public User() {}

    public User(String email, Biography biography) {
        this.email = email;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Biography getBiography() {
        return biography;
    }
}
