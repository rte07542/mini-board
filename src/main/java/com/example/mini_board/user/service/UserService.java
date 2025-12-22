package com.example.mini_board.user.service;


import com.example.mini_board.user.entity.User;
import com.example.mini_board.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long signup(String userid, String password) {
        if (userRepository.existsByUserid(userid)) {
            throw new IllegalArgumentException("이미 존재하는 아이디");
        }

        String hashed = passwordEncoder.encode(password);
        User user = new User(userid, hashed);
        return userRepository.save(user).getId();
    }

}
