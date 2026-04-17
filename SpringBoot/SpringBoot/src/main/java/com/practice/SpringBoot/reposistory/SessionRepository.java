package com.practice.SpringBoot.reposistory;

import com.practice.SpringBoot.entity.Session;
import com.practice.SpringBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
