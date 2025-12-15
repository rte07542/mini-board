package com.example.post.service;

import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //목록
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    //한건 조회
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    //생성
    public Post create(String content) {
        Post post = new Post(content);
        return postRepository.save(post);
    }

    //수정
    @Transactional //이게 뭔지 공부해두기
    public void update(Long id, String content) {
        Post post = postRepository.findById(id).orElseThrow();
        post.updateContent(content);

    }
}
