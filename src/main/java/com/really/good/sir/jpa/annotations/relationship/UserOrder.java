package com.really.good.sir.jpa.annotations.relationship;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_order")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @ManyToMany
    @JoinTable(
            name = "order_tag",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @OrderColumn(name = "tag_order_index")
    private List<Tag> tags = new ArrayList<>();

    public Long getId() { return id; }

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

    public UserAccount getUserAccount() { return userAccount; }
    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }

    public List<Tag> getTags() { return tags; }
}
