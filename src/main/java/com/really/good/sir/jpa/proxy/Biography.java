package com.really.good.sir.jpa.proxy;

import javax.persistence.*;

@Entity
@Table(name = "biographies")
public class Biography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public Biography() {}

    public Biography(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
