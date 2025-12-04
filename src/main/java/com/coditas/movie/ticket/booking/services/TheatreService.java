package com.coditas.movie.ticket.booking.services;

import com.coditas.movie.ticket.booking.constants.Role;
import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.TheatreDto;
import com.coditas.movie.ticket.booking.dto.TheatreResponseDto;
import com.coditas.movie.ticket.booking.dto.UpdateTheatreDto;
import com.coditas.movie.ticket.booking.entity.Theatre;
import com.coditas.movie.ticket.booking.entity.Users;
import com.coditas.movie.ticket.booking.exceptions.UnauthorizedAccessException;
import com.coditas.movie.ticket.booking.repositories.TheatreRepository;
import com.coditas.movie.ticket.booking.repositories.UserRepository;
import com.coditas.movie.ticket.booking.specifications.TheatreSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final UserRepository userRepository;
    public ApiResponseDto<Void> createNewTheatre(TheatreDto theatreDto) {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        Users loggedUser=userRepository.findByEmail(email);
        if (loggedUser.getRole()!=Role.THEATRE_OWNER)
        {
            throw  new UnauthorizedAccessException("Only theatre owner can create theatres");
        }
        Theatre theatre = new Theatre();
        Users user =  userRepository.findById(theatreDto.getOwner_id()).orElseThrow( ()-> new RuntimeException("user not found"));
        theatre.setName(theatreDto.getName());
        theatre.setCity(theatreDto.getCity());
        theatre.setOwner(user);
        theatreRepository.save(theatre);
        return new ApiResponseDto<>("theatre created successfully",true,null);
    }

    public Page<TheatreResponseDto> getAllTheatresForCurrentTheatreOwner(int pageSize, int pageNumber, String search, String sortDirection,String sortBy)
    {
        String  email =SecurityContextHolder.getContext().getAuthentication().getName();;
       Users user= userRepository.findByEmail(email);
        if(user.getRole()!= Role.THEATRE_OWNER)
        {
            throw new RuntimeException("user is not theatre owner");
        }
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Specification<Theatre> specification = Specification.where(
                TheatreSpecifications.hasOwner_id(user.getId())
        );
        if(search!=null && !search.isEmpty())
        {
            Specification.where(TheatreSpecifications.hasName(search));
        }

        Page<Theatre> theatreList=theatreRepository.findAll(specification,pageable);
       return theatreList.map(t-> new TheatreResponseDto(t.getName(),
                t.getCity()));

    }

    public ApiResponseDto<Void> updateTheatre(Long theatreId, UpdateTheatreDto dto) {
        Optional<Theatre> theatreOpt = theatreRepository.findById(theatreId);
        if (theatreOpt.isEmpty()) {
            return new ApiResponseDto<>("Theatre not found",false,null);
        }
        Theatre theatre = theatreOpt.get();
        theatre.setName(dto.getName());
        theatre.setCity(dto.getCity());
        theatreRepository.save(theatre);
        return new ApiResponseDto<>("Theatre updated successfully",true,null);
    }
}
