package com.really.good.sir.jpa.annotations.relationship;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    private Long id;

    private String fullName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserAccount userAccount;

    public Long getId() { return id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public UserAccount getUserAccount() { return userAccount; }
    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }
}
