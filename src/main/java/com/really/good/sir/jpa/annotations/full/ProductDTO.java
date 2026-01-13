package com.really.good.sir.jpa.annotations.full;

public class ProductDTO {
    private String name;
    private Long totalOrders;

    public ProductDTO(String name, Long totalOrders) {
        this.name = name;
        this.totalOrders = totalOrders;
    }

    public String getName() { return name; }
    public Long getTotalOrders() { return totalOrders; }
}
