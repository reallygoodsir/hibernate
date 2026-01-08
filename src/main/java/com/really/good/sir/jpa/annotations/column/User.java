package com.really.good.sir.jpa.annotations.column;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Basic(fetch = FetchType.LAZY, optional = false)
    private String biography;

    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    private String lastName;

    public User() {}

    public User(String email, String biography, String firstName, String lastName) {
        this.email = email;
        this.biography = biography;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
