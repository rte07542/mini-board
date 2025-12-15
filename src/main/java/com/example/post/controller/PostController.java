package com.example.post.controller;

import com.example.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts",postService.findAll());
        return "home";
    }

    @GetMapping("/post/new")
    public String newForm(){
        return "post-form";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.findById(id));
        return "post-detail";
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("post",postService.findById(id));
        return "post-edit";
    }

    @PostMapping("/post")
    public String create(@RequestParam("content") String content) {
        postService.create((content));
        return "redirect:/"; //글 저장 후 자동으로 메인으로 넘어간다
    }

    @PostMapping("/post/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam("content") String content){
        postService.update(id, content);
        return "redirect:/post/"+id;
    }

    @PostMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/";
    }
}
