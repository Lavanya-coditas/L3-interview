package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long>
{

}
