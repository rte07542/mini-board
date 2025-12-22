package com.example.mini_board.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String userid;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    protected User() { }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {return id;}
    public String getUserid() {return userid;}
    public String getPassword() {return password;}
    public LocalDateTime getCreatedAt() { return createdAt; }


    public User(String userid, String password){
        this.userid = userid;
        this.password = password;
    }
}
