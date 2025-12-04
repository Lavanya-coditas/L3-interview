package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long>, JpaSpecificationExecutor<Theatre> {
public Theatre findByName(String theatreName);
}
