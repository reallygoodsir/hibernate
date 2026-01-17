package com.really.good.sir.jpa.annotations.converter;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StatusConverter.class)
    private String status;

    public User() {}

    public User(String status) {
        this.status = status;
    }

    @PrePersist
    private void onPrePersist() {
        System.out.println("PrePersist triggered");
    }

    @PostPersist
    private void onPostPersist() {
        System.out.println("PostPersist triggered");
    }

    @PreUpdate
    private void onPreUpdate() {
        System.out.println("PreUpdate triggered");
    }

    @PostUpdate
    private void onPostUpdate() {
        System.out.println("PostUpdate triggered");
    }

    @PreRemove
    private void onPreRemove() {
        System.out.println("PreRemove triggered");
    }

    @PostRemove
    private void onPostRemove() {
        System.out.println("PostRemove triggered");
    }

    @PostLoad
    private void onPostLoad() {
        System.out.println("PostLoad triggered");
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
