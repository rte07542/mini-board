package com.example.mini_board.posts.service;

import com.example.mini_board.posts.dto.PostViewDto;
import com.example.mini_board.posts.entity.Post;
import com.example.mini_board.posts.repository.PostRepository;
import com.example.mini_board.user.entity.User;
import com.example.mini_board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //목록
    public List<PostViewDto> findAll(){
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(post -> {
                        String userid = userRepository.findById(post.getUserId())
                                .map(User::getUserid)
                                .orElse("알 수 없음");

                        return  new PostViewDto(
                                post.getId(),
                                post.getContent(),
                                userid
                        );
                })
                .toList();
    }

    public PostViewDto findViewById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        String userid = userRepository.findById(post.getUserId())
                .map(User::getUserid)
                .orElse("알 수 없음");

        return new PostViewDto(
                post.getId(),
                post.getContent(),
                userid
        );
    }

    public Post getPostOrThrow(Long id){
        return  postRepository.findById(id).orElseThrow();
    }

    //생성
    @Transactional
    public void create(String content, Long userId) {
        postRepository.save(new Post(content, userId));
    }

    //수정
    @Transactional //이게 뭔지 공부해두기
    public void update(Long postid, String content, Long loginUserId) {
        Post post = postRepository.findById(postid).orElseThrow();

        if (post.getUserId() == null || !post.getUserId().equals(loginUserId)){
            throw new IllegalArgumentException("권한 없음");
        }

        post.updateContent(content);
    }

    //삭제
    @Transactional
    public void delete(Long postId, Long loginUserId) {
        Post post = postRepository.findById(postId).orElseThrow();
        if (!post.getUserId().equals(loginUserId)){
            throw new IllegalStateException("권한 없음");
        }
        postRepository.delete(post);
    }
}
