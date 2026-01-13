package com.really.good.sir.jpa.annotations.relationship;

import javax.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    public Long getId() { return id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
