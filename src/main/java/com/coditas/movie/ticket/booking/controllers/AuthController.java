package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.*;
import com.coditas.movie.ticket.booking.services.RefreshTokenService;
import com.coditas.movie.ticket.booking.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController
{

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @Operation(summary = "register user")
    public ApiResponseDto<Void> registerUser(@Valid @RequestBody RegisterDto registerDto)
    {
        return userService.registerUser(registerDto);
    }

    @PostMapping("/login")
    @Operation(summary = "login for everyone")
    public ApiResponseDto<LoginResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto)
    {
        return userService.loginUser(loginDto);
    }
    @PostMapping("/refresh")
    @Operation(summary = "Generate new access token using refresh token")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        AccessTokenResponse response = refreshTokenService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }


}
