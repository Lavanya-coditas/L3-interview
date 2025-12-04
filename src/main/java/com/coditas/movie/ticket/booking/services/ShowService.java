package com.coditas.movie.ticket.booking.services;
import com.coditas.movie.ticket.booking.constants.Role;
import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.CreateShowDto;
import com.coditas.movie.ticket.booking.dto.ShowResponseDto;
import com.coditas.movie.ticket.booking.entity.Movie;
import com.coditas.movie.ticket.booking.entity.Screen;
import com.coditas.movie.ticket.booking.entity.Show;
import com.coditas.movie.ticket.booking.entity.Users;
import com.coditas.movie.ticket.booking.exceptions.InvalidShowTimingException;
import com.coditas.movie.ticket.booking.exceptions.ResourceNotFoundException;
import com.coditas.movie.ticket.booking.exceptions.UnauthorizedAccessException;
import com.coditas.movie.ticket.booking.repositories.MovieRepository;
import com.coditas.movie.ticket.booking.repositories.ScreenRepository;
import com.coditas.movie.ticket.booking.repositories.ShowRepository;
import com.coditas.movie.ticket.booking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;

    public ApiResponseDto<Void> createShowForScreens(CreateShowDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users loggedUser = userRepository.findByEmail(email);
        if (loggedUser.getRole() != Role.THEATRE_OWNER) {
            throw new UnauthorizedAccessException("Only theatre owners can create shows");
        }
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Screen screen = screenRepository.findById(dto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        if (!screen.getTheatre().getOwner().getId().equals(loggedUser.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to create shows for this screen");
        }

        LocalDateTime startTime = LocalDateTime.parse(dto.getStartTime());
        LocalDateTime endTime = startTime.plusHours(movie.getDurationHours());

        List<Show> existingShows = showRepository.findByScreenId(screen.getId());

        boolean isOverlapping = existingShows.stream().anyMatch(show ->
                startTime.isBefore(show.getEndTime()) && endTime.isAfter(show.getStartTime())
        );

        if (isOverlapping) {
            throw new InvalidShowTimingException("Show timing conflicts with an existing show on this screen");
        }

        Show newShow = new Show();
        newShow.setMovie(movie);
        newShow.setScreen(screen);
        newShow.setStartTime(startTime);
        newShow.setEndTime(endTime);
        newShow.setPrice(dto.getPrice());

        showRepository.save(newShow);

        return new ApiResponseDto<>("Show created successfully", true, null);
    }

    public Page<ShowResponseDto> getAllShows(int pageSize, int pageNumber, String sortBy, String sortDirection, String search) {

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Show> showPage;

        if (search != null && !search.isEmpty()) {
            showPage = showRepository.findByMovie_MovieNameContainingIgnoreCase(search, pageable);
        } else {
            showPage = showRepository.findAll(pageable);
        }

        return showPage.map(show ->
                new ShowResponseDto(
                        show.getId(),
                        show.getMovie().getMovieName(),
                        show.getScreen().getTheatre().getName(),
                        show.getScreen().getScreenName(),
                        show.getStartTime(),
                        show.getEndTime(),
                        show.getPrice()
                ));
    }
}
