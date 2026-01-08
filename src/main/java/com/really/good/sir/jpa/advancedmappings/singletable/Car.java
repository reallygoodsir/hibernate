package com.really.good.sir.jpa.advancedmappings.singletable;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {
    @Column(name = "doors")
    private int doors;

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }
}
