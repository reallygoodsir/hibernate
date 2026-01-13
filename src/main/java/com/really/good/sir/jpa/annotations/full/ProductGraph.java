package com.really.good.sir.jpa.annotations.full;

import javax.persistence.*;

@Entity
@NamedEntityGraph(
        name = "Product.withRatings",
        attributeNodes = @NamedAttributeNode("ratings")
)
public class ProductGraph extends Product {}
