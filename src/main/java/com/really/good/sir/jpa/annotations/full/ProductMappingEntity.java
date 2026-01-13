package com.really.good.sir.jpa.annotations.full;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "ProductMapping",
        classes = @ConstructorResult(
                targetClass = ProductDTO.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "total_orders", type = Long.class)
                }
        )
)
@Entity
public class ProductMappingEntity {
    @Id
    private Long id; // dummy entity for mapping
}
