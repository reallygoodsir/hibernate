package com.really.good.sir.jpa.annotations.enums;

import javax.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.ORDINAL)
    private Status statusOrdinal;

    @Enumerated(EnumType.STRING)
    private Status statusString;

    public UserAccount() {}

    public UserAccount(String username, Status statusOrdinal, Status statusString) {
        this.username = username;
        this.statusOrdinal = statusOrdinal;
        this.statusString = statusString;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Status getStatusOrdinal() { return statusOrdinal; }
    public Status getStatusString() { return statusString; }

    public void setUsername(String username) { this.username = username; }
    public void setStatusOrdinal(Status statusOrdinal) { this.statusOrdinal = statusOrdinal; }
    public void setStatusString(Status statusString) { this.statusString = statusString; }
}
