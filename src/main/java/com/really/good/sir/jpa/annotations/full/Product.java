package com.really.good.sir.jpa.annotations.full;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
@NamedQuery(
        name = "Product.findAll",
        query = "SELECT p FROM Product p"
)
@NamedNativeQuery(
        name = "Product.findAllNative",
        query = "SELECT id, name FROM products",
        resultSetMapping = "ProductMapping"
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // OneToMany with Order
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @OrderBy("quantity DESC")             // orders by quantity
    @OrderColumn(name = "order_index")    // stores actual list order
    private List<Order> orders = new ArrayList<>();

    // ElementCollection with map
    @ElementCollection
    @CollectionTable(
            name = "product_ratings",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @MapKeyColumn(name = "rating_type")   // column name for the key
    @MapKeyEnumerated(EnumType.STRING)    // store enum as string
    @MapKeyClass(Rating.class)            // key type
    @Column(name = "value")
    private Map<Rating, String> ratings = new HashMap<>();

    public Product() {}
    public Product(String name) { this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Order> getOrders() { return orders; }
    public Map<Rating, String> getRatings() { return ratings; }
}
