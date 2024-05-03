package manageo.reservation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import manageo.reservation.persistence.entities.ReservationEntity;
import manageo.reservation.services.ReservationService;
import manageo.reservation.services.RestaurantService;
import manageo.reservation.web.converters.ReservationConverter;
import manageo.reservation.web.dto.ReservationConfirmationDto;
import manageo.reservation.web.dto.ReservationDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

@RestController
public class ReservationController {

    @Autowired
    ReservationService service;

    @Autowired
    RestaurantService restaurantService;

    ReservationConverter converter = new ReservationConverter();

    @GetMapping( path="/")
    @Operation(summary = "List reservations")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return a page of reservations", 
          content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Page.class ) ) } ) } )
    ResponseEntity<Page<ReservationDto>> listReservations(@RequestParam(required = false, defaultValue = "0") Integer page, 
                                        @RequestParam(required = false, defaultValue = "10") Integer size ) {
        Page<ReservationEntity> pageEntity = service.listReservations( PageRequest.of(page, size) );
        return ResponseEntity.ok().body( converter.convert( pageEntity ) );
    }

    @PostMapping( path="/" )
    @Operation(summary = "Post a reservation")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return the reservation confimration", 
          content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ReservationConfirmationDto.class ) ) } ) } )

    ReservationConfirmationDto reserve( @RequestBody ReservationDto reservation ) {
        ReservationConfirmationDto dto = new ReservationConfirmationDto();
        dto.setEmail( reservation.getEmail() );
        dto.setDate( reservation.getDate() );
        dto.setRestaurantId( reservation.getRestaurantId() );
        if( restaurantService.isAvailable(reservation) ) {
            ReservationEntity entity = service.saveReservation( converter.convert(reservation) );
            dto.setConfirmed( true );
            dto.setId( entity.getId() );
        } else {
            dto.setConfirmed( false );
        }
        return dto;
    }

}
