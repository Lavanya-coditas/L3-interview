package com.coditas.movie.ticket.booking.services;

import com.coditas.movie.ticket.booking.Utils.AuthUtils;
import com.coditas.movie.ticket.booking.dto.AccessTokenResponse;
import com.coditas.movie.ticket.booking.entity.RefreshToken;
import com.coditas.movie.ticket.booking.entity.Users;
import com.coditas.movie.ticket.booking.repositories.RefreshTokenRepository;
import com.coditas.movie.ticket.booking.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RefreshTokenService {

    private  final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AuthUtils authUtil;


    public AccessTokenResponse refreshAccessToken(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (isTokenExpired(token)) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }

        Users user = token.getUser();
        String newAccessToken = authUtil.generateAccessToken(user);

        return new AccessTokenResponse(newAccessToken);
    }

    public boolean isTokenExpired(RefreshToken token) {

        return token.getExpiryDate().isBefore(Instant.now());
    }

    public RefreshToken createRefreshToken(Long userId) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
       //unoque refresh tokens
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        return refreshTokenRepository.save(refreshToken);
    }

}
