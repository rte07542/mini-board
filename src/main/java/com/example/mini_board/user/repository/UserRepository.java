package com.example.mini_board.user.repository;


import com.example.mini_board.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid); //로그인/조회용
    boolean existsByUserid(String userid);
    Optional<User> findById(Long id);//회원가입 중복 체크용
}
