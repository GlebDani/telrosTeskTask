package ru.danilenko.telros.backend.entity.security;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "auth_info")
public class AuthInfo {

    @Id
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "auth_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authTime;

    public AuthInfo() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }
}
