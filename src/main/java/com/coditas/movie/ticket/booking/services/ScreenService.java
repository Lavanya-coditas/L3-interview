package com.coditas.movie.ticket.booking.services;

import com.coditas.movie.ticket.booking.constants.Role;
import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.ScreenDto;
import com.coditas.movie.ticket.booking.dto.UpdateScreenDto;
import com.coditas.movie.ticket.booking.entity.Screen;
import com.coditas.movie.ticket.booking.entity.Theatre;
import com.coditas.movie.ticket.booking.entity.Users;
//import com.coditas.movie.ticket.booking.exceptions.UserNotFoundException;
import com.coditas.movie.ticket.booking.repositories.ScreenRepository;
import com.coditas.movie.ticket.booking.repositories.TheatreRepository;
import com.coditas.movie.ticket.booking.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final UserRepository userRepository;
    private final TheatreRepository theatreRepository;

    public ApiResponseDto<Void> createScreen(ScreenDto screenDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        if (user.getRole() != Role.THEATRE_OWNER) {
            throw new RuntimeException("User is not theatre owner");
        }
        Theatre theatre = theatreRepository.findById(screenDto.getTheatreId()).orElseThrow(() -> new RuntimeException("THeatre not found"));
        if (theatre.getOwner().getId() != user.getId()) {
            throw new RuntimeException("OWNER entered is not theatre owner of this theatre");
        }

        Screen screen = screenRepository.findByScreenName(screenDto.getName());
        if (screen != null) {
            throw new RuntimeException("Screen ALready Present");
        }
        Screen screen1=new Screen();
        screen1.setScreenName(screenDto.getName());
        screen1.setSeatsPerRow(screenDto.getSeatsPerRow());
        screen1.setTotalRows(screenDto.getTotalRows());
        screen1.setTheatre(theatre);
        screenRepository.save(screen1);
        return new ApiResponseDto<>("screen created succesfully", true, null);
    }

    public ApiResponseDto<List<Screen>> getTheatreScreen() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        if (user.getRole() != Role.THEATRE_OWNER) {
            throw new RuntimeException("User is not theatre owner");
        }
        List<Screen> screens = screenRepository.findAll();
        List<Screen> finalScreens = new ArrayList<>();
        for (Screen screen : screens) {
            String theatreName = screen.getTheatre().getName();
            Theatre theatre = theatreRepository.findByName(theatreName);

            if (theatre.getOwner() == user) {
                finalScreens.add(screen);
            }
        }
        return new ApiResponseDto<>("fetched succesfully", true, finalScreens);
    }

    public ApiResponseDto<String> updateScreen(Long screenId, UpdateScreenDto updateScreenDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        if (user.getRole() != Role.THEATRE_OWNER) {
            throw new RuntimeException("User is not theatre owner");
        }
        Optional<Screen> screen = screenRepository.findById(screenId);
        if (screen.isEmpty()) {
            return new ApiResponseDto<>("Theatre not found", false, null);
        }

        Screen screen1 = screen.get();
        screen1.setScreenName(updateScreenDto.getName());
        screen1.setSeatsPerRow(updateScreenDto.getSeatsPerRow());
        screen1.setTotalRows(updateScreenDto.getTotalRows());
       screenRepository.save(screen1);
       return  new ApiResponseDto<>("updated Succesfully",true,"false");
    }
}
