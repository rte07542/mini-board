package com.example.mini_board.post.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //작성자 (User.id)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 2000)
    private String content;

    //JPA를 위한 생성자, 사람은 쓰지않는다.
    // protected Post() {}

    //사람이 쓰는 생성자
    public  Post(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }

    // content는 직접 수정하지 않고, 이 메서드를 통해서만 변경한다.
    // 이유? 엔티티 상태 변경 규칙을 한 곳에서 관리하기 위함
    public void updateContent(String content){
        this.content = content;
    }

    // id, content, reatedAt, updateAt는 이 필드에서만 건들 수 있다. 읽기만 가능하게 만듬.
    public Long getId(){
        return id;
    }
    public String getContent(){
        return content;
    }
    public Long getUserId() {return userId;}


}
