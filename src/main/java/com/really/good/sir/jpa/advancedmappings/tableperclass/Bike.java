package com.really.good.sir.jpa.advancedmappings.tableperclass;

import javax.persistence.*;

@Entity
public class Bike extends Vehicle {
    @Column(name = "has_pedals", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean hasPedals;

    public boolean isHasPedals() {
        return hasPedals;
    }

    public void setHasPedals(boolean hasPedals) {
        this.hasPedals = hasPedals;
    }
}
