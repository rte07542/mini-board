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
    private final PostRepository postRepository; //게시글 db 접근용
    private final UserRepository userRepository; //유저 db 접근용 (작성자 userid 조회에서 사용)

    /*
    * 게시글 목록 조회 (홈 화면)
    * Post 엔티티 → PostViewDto로 변환
    * 화면에 필요한 데이터만 담는다
    */
    public List<PostViewDto> findAll(){
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(post -> {
                        Long authorId = post.getUserId(); //작성자 PK (users.id)
                        String authorUsername = findAuthorUsername(authorId); //작성자 userid(문자열 아이디)

                        // 화면용 DTO로 변환
                        return  new PostViewDto(
                                post.getId(),
                                post.getContent(),
                                authorUsername,
                                authorId
                        );
                })
                .toList();
    }

    /*
    * 게시글 상세 조회 (상세 페이지)
    * 엔티티 그대로 쓰지 않고 DTO로 내려준다
    * 이유 : 화면에 필요 없는 필드 노출 방지
    * */
    public PostViewDto findViewById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        Long authorId = post.getUserId();

        String authorUsername = findAuthorUsername(authorId);

        return new PostViewDto(
                post.getId(),
                post.getContent(),
                authorUsername,
                authorId
        );
    }

    /*
    * 수정/삭제/권한 체크용
    * 엔티티가 필요할 때만 사용
    * 화면 렌더링에는 절대 쓰지 않는다.
    */
    public Post getPostOrThrow(Long id){
        return  postRepository.findById(id).orElseThrow();
    }

    /*
    * 게시글 생성
    * 로그인한 유저의 ID를 함께 저장
    */
    @Transactional
    public void create(String content, Long userId) {
        postRepository.save(new Post(content, userId));
    }

    /*
    * 게시글 수정
    * 작성자 본인만 가능
    * 트랜잭션 안에서 dirty checking으로 update 수행
    */
    @Transactional //이게 뭔지 공부해두기
    public void update(Long postid, String content, Long loginUserId) {
        Post post = postRepository.findById(postid).orElseThrow();

        //권한 체크
        if (post.getUserId() == null || !post.getUserId().equals(loginUserId)){
            throw new IllegalArgumentException("권한 없음");
        }

        //내용 수정
        post.updateContent(content);
    }

    /*
    * 게시글 삭제
    * 작성자 본인만 가능
    */
    @Transactional
    public void delete(Long postId, Long loginUserId) {
        Post post = postRepository.findById(postId).orElseThrow();
        if (!post.getUserId().equals(loginUserId)){
            throw new IllegalStateException("권한 없음");
        }
        postRepository.delete(post);
    }

    /*
    * 작성자 userid 조회 공통 메서드
    * userId가 null이거나
    * 유저가 삭제된 경우 대비
    */
    private String findAuthorUsername(Long authorId){
        if(authorId == null) {
            return "알 수 없음";
        }
        return userRepository.findById(authorId)
                .map(User::getUserid)
                .orElse("알 수 없음");
    }
}
