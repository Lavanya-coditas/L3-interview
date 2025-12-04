package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
