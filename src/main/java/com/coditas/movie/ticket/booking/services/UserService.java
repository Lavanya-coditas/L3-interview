package com.coditas.movie.ticket.booking.services;

import com.coditas.movie.ticket.booking.Utils.AuthUtils;
import com.coditas.movie.ticket.booking.constants.Role;
import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.LoginDto;
import com.coditas.movie.ticket.booking.dto.LoginResponseDto;
import com.coditas.movie.ticket.booking.dto.RegisterDto;
import com.coditas.movie.ticket.booking.entity.RefreshToken;
import com.coditas.movie.ticket.booking.entity.Users;
import com.coditas.movie.ticket.booking.exceptions.InvalidCredentialsException;
import com.coditas.movie.ticket.booking.exceptions.UserAlreadyExistsException;
import com.coditas.movie.ticket.booking.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService
{

    private final UserRepository userRepository;
    private final AuthUtils authUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RefreshTokenService refreshTokenService;

    public ApiResponseDto<Void> registerUser(RegisterDto registerDto) {
        Users existingUser = userRepository.findByEmail(registerDto.getEmail());
        if (existingUser!=null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        Users user = new Users();
        user.setRole(Role.USER);

        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());
        userRepository.save(user);
        return new ApiResponseDto<>("User created successfully", true, null);
    }

    public ApiResponseDto<LoginResponseDto> loginUser(LoginDto loginDto)
    {
        Users user = userRepository.findByEmail(loginDto.getEmail());
        if (user==null)
        {
            throw new RuntimeException("user not found");
        }
        //check pasword
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String accessToken = authUtil.generateAccessToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setId(user.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setRole(user.getRole());
        responseDto.setAccessToken(accessToken);
        responseDto.setRefreshToken(refreshToken.getToken());  // refresh token from DB

        return new ApiResponseDto<>("Login success", true, responseDto);
    }

}
