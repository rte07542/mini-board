package com.example.post.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //글, 작성시간, 수정시간
    @Column(nullable = false, length = 2000)
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    protected Post() {
        //JPA를 위한 생성자, 사람은 쓰지않는다.
    }

    //새 글 하나 만들고싶다. 추후에 new Post("글 내용"); 으로 사용된다.
    public Post(String content){
        this.content = content;
    }

    //글 작성하고 db에 처음 저장되기 직전에 호출된다. 생성시간, 수정시간이 자동 설정됨
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updateAt = this.createdAt;
    }

    //수정될 때. 수정된 시간
    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    // id, content, reatedAt, updateAt는 이 필드에서만 건들 수 있다. 읽기만 가능하게 만듬.
    public Long getId(){
        return id;
    }
    public String getContent(){
        return content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdateAt(){
        return updateAt;
    }

    // content는 직접 수정하지 않고, 이 메서드를 통해서만 변경한다.
    // 이유? 엔티티 상태 변경 규칙을 한 곳에서 관리하기 위함
    public void updateContent(String content){
        this.content = content;
    }
}
