package com.coditas.movie.ticket.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_table")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String movieName;

    @Column
    private int durationHours;

    @Column
    private String genre;

    @Column
    private String movieLanguage;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre_id;


}
