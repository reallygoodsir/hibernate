package com.really.good.sir.jpa;

import javax.persistence.*;

//@Entity
//@Table(name = "users")
public class UserEntity {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "name")
    private String userName;

    //@Column(name = "email")
    private String email;

    protected UserEntity() {
        // required by JPA
    }

    public UserEntity(String name, String email) {
        this.userName = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
