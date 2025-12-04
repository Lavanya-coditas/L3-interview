package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.entity.Screen;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {
    Screen findByScreenName( String name);
}
