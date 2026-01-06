package com.really.good.sir.jpa.advancedmappings.singletable;

import javax.persistence.*;

@Entity
@DiscriminatorValue("BIKE")
public class Bike extends Vehicle {
    @Column(name = "has_pedals")
    private boolean hasPedals;

    public boolean isHasPedals() {
        return hasPedals;
    }

    public void setHasPedals(boolean hasPedals) {
        this.hasPedals = hasPedals;
    }
}
