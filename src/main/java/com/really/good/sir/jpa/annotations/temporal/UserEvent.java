package com.really.good.sir.jpa.annotations.temporal;

import javax.persistence.*;
        import java.util.Date;

@Entity
@Table(name = "user_event")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_time", nullable = false)
    private Date loginTime;

    @Transient
    private String debugInfo; // will NOT be persisted

    public UserEvent() {}

    public UserEvent(String username, Date loginTime, String debugInfo) {
        this.username = username;
        this.loginTime = loginTime;
        this.debugInfo = debugInfo;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }
}
