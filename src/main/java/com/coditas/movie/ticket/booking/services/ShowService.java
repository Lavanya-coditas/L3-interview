package com.coditas.movie.ticket.booking.services;

import com.coditas.movie.ticket.booking.constants.Role;
import com.coditas.movie.ticket.booking.dto.ShowDto;
import com.coditas.movie.ticket.booking.entity.Screen;
import com.coditas.movie.ticket.booking.entity.Show;
import com.coditas.movie.ticket.booking.entity.Theatre;
import com.coditas.movie.ticket.booking.entity.Users;
import com.coditas.movie.ticket.booking.exceptions.ResourceNotFoundException;
import com.coditas.movie.ticket.booking.exceptions.UnauthorizedAccessException;
import com.coditas.movie.ticket.booking.repositories.ScreenRepository;
import com.coditas.movie.ticket.booking.repositories.ShowRepository;
import com.coditas.movie.ticket.booking.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final ScreenRepository screenRepo;

    public void createShowForScreens(ShowDto showDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userRepository.findByEmail(email);
        if (users.getRole() != Role.THEATRE_OWNER) {
            throw new UnauthorizedAccessException("Only theatre owner has access");
        }
        Optional<Screen> givenScreen = screenRepo.findById(showDto.getScreenId());
        Screen screenFetched = givenScreen.get();
        if(screenFetched==null)
        {
            throw new ResourceNotFoundException("No screen found ");
        }
        Theatre theatre = screenFetched.getTheatre();
        if (theatre == null) {
            throw new ResourceNotFoundException("no theatre found for the entered data");
        }
       Show show = new Show();



    }


}
