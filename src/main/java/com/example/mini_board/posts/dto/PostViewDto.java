package com.example.mini_board.posts.dto;

public class PostViewDto {
    private final Long id;
    private final String content;
    private final String authorUsername;
    private final Long authorId;

    public PostViewDto(Long id, String content, String authorUsername, Long authorId){
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.authorId = authorId;
    }

    public Long getId() { return id;}
    public String getContent() {return content;}
    public String getAuthorUsername() {return authorUsername;}
    public Long authorId() {return  authorId;}
}
