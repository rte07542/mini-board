package com.example.mini_board.user.controller;


import com.example.mini_board.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestParam String userid,
                         @RequestParam String password) {
        Long id = userService.signup(userid, password);
        return  "가입됨.";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "users/signup";
    }
}
