package com.example.post.repository;

import com.example.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//Post : 다룰 엔티티, Long : PK타입
public interface PostRepository extends JpaRepository<Post, Long> {
}
