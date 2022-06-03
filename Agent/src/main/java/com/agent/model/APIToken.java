package com.agent.model;

import javax.persistence.*;

@Entity
public class APIToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", length = 1024)
    private String token;

    @OneToOne
    private User user;

    public APIToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public APIToken() {

    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
