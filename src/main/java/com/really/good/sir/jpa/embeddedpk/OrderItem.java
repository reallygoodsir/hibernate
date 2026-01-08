package com.really.good.sir.jpa.embeddedpk;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    private int quantity;

    public OrderItem() {
    }

    public OrderItem(OrderItemId id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public OrderItemId getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
