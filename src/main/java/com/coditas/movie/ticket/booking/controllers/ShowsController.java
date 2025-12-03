package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.ShowDto;
import com.coditas.movie.ticket.booking.services.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shows")
@Tag(name = "Shows controller ", description = "shows for the screens ")
@AllArgsConstructor
public class ShowsController
{
    private final ShowService showsService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('THEATRE_OWNER')")
    public ApiResponseDto<Void> createShow(@Valid @RequestBody ShowDto showDto)
    {
      showsService.createShowForScreens(showDto);
      return new ApiResponseDto<>("show created succesfuly",true,null);
    }

}
