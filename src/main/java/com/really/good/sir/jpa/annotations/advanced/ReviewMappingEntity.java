package com.really.good.sir.jpa.annotations.advanced;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "ReviewSummaryMapping",
        classes = @ConstructorResult(
                targetClass = ReviewSummary.class,
                columns = {
                        @ColumnResult(name = "reviewer", type = String.class),
                        @ColumnResult(name = "rating_count", type = Integer.class)
                }
        )
)
@Entity
public class ReviewMappingEntity {
    @Id
    private Long id; // dummy entity to attach mapping
}
