package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.entity.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long>
{

    List<Show> findByScreenId(Long id);

    Page<Show> findByMovie_MovieNameContainingIgnoreCase(String search, Pageable pageable);

}
