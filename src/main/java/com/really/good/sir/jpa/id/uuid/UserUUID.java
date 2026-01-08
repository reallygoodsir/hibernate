package com.really.good.sir.jpa.id.uuid;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users_uuid")
public class UserUUID {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;
    private int age;

    public UserUUID() {}
    public UserUUID(String name, int age) { this.name = name; this.age = age; }

    public String getId() { return id; }
}
