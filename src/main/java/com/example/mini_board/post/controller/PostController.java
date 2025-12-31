package com.example.mini_board.post.controller;

import com.example.mini_board.post.dto.PostCreateRequest;
import com.example.mini_board.post.dto.PostUpdateRequest;
import com.example.mini_board.post.entity.Post;
import com.example.mini_board.post.service.PostService;
import com.example.mini_board.user.controller.AuthController;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

       if (post.getUserId() == null || !post.getUserId().equals(loginUserId)){
           redirectAttributes.addFlashAttribute("error", "권한이 없습니다.");
           return "redirect:/post/" + id;
       }
       model.addAttribute("post", post);
       model.addAttribute("postId", id);
       model.addAttribute("content",post.getContent());
       return "post-edit";
    }

    @PostMapping("/post")
    public String create(@Valid PostCreateRequest req,
                         BindingResult bindingResult,
                         HttpSession session,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            // 에러 메시지 하나만 뽑아서 flash로 보내기 (최소 구현)
            String msg = bindingResult.getFieldError().getDefaultMessage();
            redirectAttributes.addFlashAttribute("error",msg);
            return "redirect:/post/new";
        }
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        if (loginUserId == null) return "redirect:/auth/login";

        postService.create(req.getContent(),loginUserId);
        redirectAttributes.addFlashAttribute("msg", "작성완료");
        return "redirect:/";
    }


    @PostMapping("/post/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid PostUpdateRequest req,
                       BindingResult bindingResult,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes){
        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        if (loginUserId == null) return "redirect:/auth/login";

        //원글 가져와서 권한 체크
        Post post = postService.getPostOrThrow(id);
        if (!post.getUserId().equals(loginUserId)) {
            redirectAttributes.addFlashAttribute("error", "권한이 없습니다.");
            return "redirect:/post/" + id;
        }

        // 검증 실패: redirect 말고 같은 페이지 렌더링
        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
            model.addAttribute("loginUserId", loginUserId);
            model.addAttribute("postId", id);
            model.addAttribute("content", req.getContent()); //사용자가 친 값 그대로
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "post-edit";
        }

        postService.update(id, req.getContent(), loginUserId);
        redirectAttributes.addFlashAttribute("msg","수정 완료");
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
