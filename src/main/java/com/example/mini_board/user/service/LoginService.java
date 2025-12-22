package com.example.mini_board.user.service;

import com.example.mini_board.user.entity.User;
import com.example.mini_board.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String userid, String rawPassword) {
        User user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("정보 불일치"));
        if (!passwordEncoder.matches(rawPassword,user.getPassword())){
            throw new IllegalArgumentException("정보 불일치");
        }
        return user;
    }
}
