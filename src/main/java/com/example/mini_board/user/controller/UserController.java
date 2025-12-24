package com.example.mini_board.user.controller;


import com.example.mini_board.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String userid,
                         @RequestParam String password,
                         RedirectAttributes redirectAttributes) {

        userService.signup(userid, password);

        redirectAttributes.addFlashAttribute("msg", "회원가입 완료. 이제 로그인 해라.");
        return  "redirect:/";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "users/signup";
    }
}
