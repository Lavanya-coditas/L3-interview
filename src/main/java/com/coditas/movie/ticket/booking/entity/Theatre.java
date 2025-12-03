package com.coditas.movie.ticket.booking.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String city;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;


    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens;


    @OneToMany
    List<Movie> movies;



}


