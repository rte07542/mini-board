package com.example.mini_board.posts.dto;

public class PostViewDto {
    private final Long id;
    private final String content;
    private final String writerUserid;

    public PostViewDto(Long id, String content, String writerUserid){
        this.id = id;
        this.content = content;
        this.writerUserid = writerUserid;
    }

    public Long getId() { return id;}
    public String getContent() {return content;}
    public String getWriterUserid() {return writerUserid;}
}
