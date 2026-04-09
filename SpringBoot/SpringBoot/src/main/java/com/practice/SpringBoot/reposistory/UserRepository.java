package com.practice.SpringBoot.reposistory;

import com.practice.SpringBoot.entity.PostEntity;
import com.practice.SpringBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
