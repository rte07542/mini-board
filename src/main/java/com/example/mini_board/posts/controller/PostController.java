package com.example.mini_board.posts.controller;

import com.example.mini_board.posts.entity.Post;
import com.example.mini_board.posts.service.PostService;
import com.example.mini_board.user.controller.AuthController;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("posts",postService.findAll());
        return "home";
    }

    @GetMapping("/post/new")
    public String newForm(){
        return "post-form";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model){
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);

        model.addAttribute("post", postService.findViewById(id));
        model.addAttribute("loginUserId", session.getAttribute(AuthController.LOGIN_USER_ID));
        return "post-detail";
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Model model) {
       Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
       if (loginUserId == null) return "redirect:/auth/login";

       Post post = postService.getPostOrThrow(id);

       if (post.getUserId() ==null || !post.getUserId().equals(loginUserId)){
           redirectAttributes.addFlashAttribute("error", "권한이 없습니다.");
           return "redirect:/post/" + id;
       }
       model.addAttribute("post", post);
       return "post-edit";
    }

    @PostMapping("/post")
    public String create(@RequestParam("content") String content, HttpSession session) {
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        if(loginUserId == null) return "redirect:/auth/login";
        postService.create(content,loginUserId);
        return "redirect:/";
    }


    @PostMapping("/post/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam("content") String content,
                       HttpSession session){
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        if (loginUserId == null) return "redirect:/auth/login";

        postService.update(id, content, loginUserId);
        return "redirect:/post/"+id;
    }

    @PostMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        if (loginUserId == null) return "redirect:/auth/login";

        try{
            postService.delete(id, loginUserId);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/post/" + id;
        }
    }

}
