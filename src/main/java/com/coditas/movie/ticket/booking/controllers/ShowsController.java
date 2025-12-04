package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.CreateShowDto;
import com.coditas.movie.ticket.booking.dto.ShowDto;
import com.coditas.movie.ticket.booking.dto.ShowResponseDto;
import com.coditas.movie.ticket.booking.services.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shows")
@Tag(name = "Shows controller ", description = "shows for the screens ")
@AllArgsConstructor
public class ShowsController
{
    private final ShowService showsService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('THEATRE_OWNER')")
    public ApiResponseDto<Void> createShow(@Valid @RequestBody CreateShowDto showDto)
    {
      showsService.createShowForScreens(showDto);
      return new ApiResponseDto<>("show created succesfuly",true,null);
    }

    @GetMapping("/get/shows")
    @PreAuthorize("hasAuthority('USER','THEATRE_OWNER')")
    public ApiResponseDto<Page<ShowResponseDto>> getAllShows(
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "startTime",required = false) String sortBy,
            @RequestParam(defaultValue = "asc",required = false) String sortDirection,
            @RequestParam(required = false) String search) {

        Page<ShowResponseDto> shows = showsService.getAllShows(pageSize, pageNumber, sortBy, sortDirection, search);
        return new ApiResponseDto<>("Shows fetched succesfuly", true, shows);
    }


}
