package com.coditas.movie.ticket.booking.entity;

import com.coditas.movie.ticket.booking.constants.AccountStatus;
import com.coditas.movie.ticket.booking.constants.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column
    Role role;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Enumerated(value = EnumType.STRING)
    @Column
    private AccountStatus isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }
}
