package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.entity.Session;
import com.practice.SpringBoot.entity.User;
import com.practice.SpringBoot.reposistory.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);
        if (userSessions.size() == SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentSession);
        }

        // Now creating new session
        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .lastUsedAt(LocalDateTime.now())
                .build();
        sessionRepository.save(newSession);
    }

    // checking session in db , according to refreshtoken or not.
    public boolean validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Session not found for refreshToken" + refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);

        return true;

    }

    public void rotateSession(String oldRefreshToken, String newRefreshToken) {
        Session session = sessionRepository.findByRefreshToken(oldRefreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("Session not found for refreshToken" + oldRefreshToken));

        session.setRefreshToken(newRefreshToken);
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    // session count in user entity or create another entity subscription entity store number of active session and according to that session you can maintain session
    // create logout controller

}
