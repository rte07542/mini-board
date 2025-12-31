package com.example.mini_board.user.entity;

import com.example.mini_board.post.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String userid;

    @Column(nullable = false,length = 255)
    private String password;

    public User(String userid, String password){
        this.userid = userid;
        this.password = password;
    }

    public Long getId() {return id;}
    public String getUserid() {return userid;}
    public String getPassword() {return password;}



}
