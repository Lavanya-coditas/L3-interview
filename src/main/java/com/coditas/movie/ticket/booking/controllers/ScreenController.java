package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.ScreenDto;
import com.coditas.movie.ticket.booking.dto.UpdateScreenDto;
import com.coditas.movie.ticket.booking.entity.Screen;
import com.coditas.movie.ticket.booking.services.ScreenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/screen")
@AllArgsConstructor
@RestController
@Tag(name = "Screeen Controllers", description = "Screen CRUD")
public class ScreenController {

    private final ScreenService screenService;
    @PostMapping("/create")
    @Operation(summary = "create screens")
    @PreAuthorize("hasAuthority('THEATRE_OWNER')")
    public ApiResponseDto<Void> CreateScreen(@Valid @RequestBody ScreenDto screenDto)
    {
       return screenService.createScreen(screenDto);
    }

    @GetMapping("/get/screens")
    @Operation(summary = "get Screen for the theatre")
    @PreAuthorize("hasAuthority('THEATRE_OWNER')")
    public  ApiResponseDto<List<Screen>> screenForTheatre()
    {
        return screenService.getTheatreScreen();
    }

    @PutMapping("/update/screen/{screenId}")
    @Operation(summary = "update Screen for the theatre")
    @PreAuthorize("hasAuthority('THEATRE_OWNER')")
    public ApiResponseDto<String> updateScreen(@PathVariable Long screenId, @RequestBody @Valid UpdateScreenDto updateScreenDto)
    {
        return screenService.updateScreen(screenId,updateScreenDto);
    }

}
