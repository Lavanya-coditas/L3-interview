package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.TheatreDto;
import com.coditas.movie.ticket.booking.dto.TheatreResponseDto;
import com.coditas.movie.ticket.booking.dto.UpdateTheatreDto;
import com.coditas.movie.ticket.booking.services.TheatreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/theatre")
@AllArgsConstructor
@Tag(name = "Theatre Controller", description = "CRUD OF THEATRE")
public class TheatreController {

    private final TheatreService theatreService;
    @PreAuthorize("hasAuthority('THEATRE_OWNER')")
    @PostMapping("/create/new")
    @Operation(summary = "new theatre")
    public ApiResponseDto<Void> createTheatre(@Valid @RequestBody TheatreDto theatreDto)
    {
        return theatreService.createNewTheatre(theatreDto);
    }
    @PreAuthorize("hasAuthority('THEATRE_OWNER')")
    @GetMapping("/get/theatres")
    @Operation(summary = "get all theatres")
    public ApiResponseDto<Page<TheatreResponseDto>> getAllTheatres(
            @RequestParam(value = "sortBy", defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc",required = false) String sorDirection,
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber)
    {

        Page<TheatreResponseDto> response = theatreService.getAllTheatresForCurrentTheatreOwner(pageSize, pageNumber, search, sorDirection, sortBy);
        return new ApiResponseDto<>("theatres fetched successfully", true, response);
    }


    @PutMapping("/update/{id}")
    @Operation(summary = "Update theatre by ID")
    public ApiResponseDto<Void> updateTheatre (@PathVariable Long id, @RequestBody UpdateTheatreDto dto)
    {
        return theatreService.updateTheatre(id, dto);
    }
}
