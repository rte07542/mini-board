package com.example.mini_board.user.controller;

import com.example.mini_board.user.entity.User;
import com.example.mini_board.user.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    public static final String LOGIN_USER_ID = "LOGIN_USER_ID";
    private final LoginService loginService;
    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userid,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            User user = loginService.login(userid, password);
            session.setAttribute(LOGIN_USER_ID, user.getId());
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("loginError", "정보 불일치");
            return "redirect:/";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
