package com.example.mini_board.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostUpdateRequest {
    @NotBlank(message = "내용은 비면 안됨")
    @Size(max = 100, message = "내용은 100자까지")
    private String content;

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
}
