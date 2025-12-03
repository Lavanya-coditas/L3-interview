package com.coditas.movie.ticket.booking.dto;

import com.coditas.movie.ticket.booking.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LoginResponseDto {

        Long id;
        String username;
        Role role;
        String accessToken;
        String refreshToken;


    }


