package com.really.good.sir.jpa.annotations.relationship;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderNumber ASC")
    private List<UserOrder> orders = new ArrayList<>();

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public UserProfile getProfile() { return profile; }
    public void setProfile(UserProfile profile) {
        this.profile = profile;
        if (profile != null) profile.setUserAccount(this);
    }

    public List<UserOrder> getOrders() { return orders; }
}
