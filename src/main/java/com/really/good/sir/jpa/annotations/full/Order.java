package com.really.good.sir.jpa.annotations.full;

import com.really.good.sir.jpa.annotations.full.Product;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Order() {}
    public Order(int quantity) { this.quantity = quantity; }

    public Long getId() { return id; }
    public int getQuantity() { return quantity; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}