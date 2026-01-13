package com.really.good.sir.jpa.annotations.queries;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(
                name = "Product.findByCategory",
                query = "SELECT p FROM Product p WHERE p.category = :cat"
        )
})
@NamedNativeQuery(
        name = "Product.nativeFindAll",
        query = "SELECT * FROM product",
        resultClass = Product.class
)
@SqlResultSetMapping(
        name = "ProductNameMapping",
        columns = @ColumnResult(name = "name")
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    // ElementCollection example: store key-value attributes
    @ElementCollection
    @CollectionTable(
            name = "product_attributes",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value")
    private Map<String, String> attributes = new HashMap<>();

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Map<String, String> getAttributes() { return attributes; }
    public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }
}
