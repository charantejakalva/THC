package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.dto.ReservationDTO;
import org.example.dto.RestaurantDTO;
import org.example.exception.ReservationServiceException;
import org.example.model.Reservation;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.service.ReservationServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

public class ReservationController {
    private final ReservationServiceImplement service;

    public ReservationController(ReservationServiceImplement service){
        this.service = service;
    }

    @GetMapping("/restaurant/{id}/reservation/{page}/{size}")
    @ApiOperation(value = "Get reservations from a particular restaurant",
            notes = "Provide needed values to get all the reservations made in the restaurant.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<ReservationDTO>> getReservations(@PathVariable(value = "id") int restaurantId,
                                                             @PathVariable(value = "page") int page,
                                                             @PathVariable(value = "size") int size){

      return  Response.<List<ReservationDTO>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()

        ).data(service.getReservations(restaurantId,page,size)).build();


    }
    @GetMapping("/restaurant/reservation/{page}/{size}")
    @ApiOperation(value = "Get reservations from all restaurants",
            notes = "Provide needed values to get all reservations made across all restaurants.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<ReservationDTO>> getAllReservations(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size){
        return  Response.<List<ReservationDTO>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()

        ).data(service.getAllReservations(page,size)).build();
    }

    @GetMapping("/restaurant/reservation/{id}")
    @ApiOperation(value = "Get reservation based on Reservation id",
            notes = "Provide needed values to get the Reservation details based on Id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<ReservationDTO> getReservationById(@PathVariable int id){

        if( service.getReservationById(id) == null){
            return  Response.< ReservationDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(404)
                    .statusMessage(HttpStatus.NOT_FOUND.toString()).build()
            ).data(null).build();
        }
        else {
            return Response.<ReservationDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.getReservationById(id))).build();
        }
    }

    @PostMapping("/restaurant/{id}/reservation")
    @ApiOperation(value = "Add a Reservation at the needed restaurant",
            notes = "Provide needed values to Add the Reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<ReservationDTO> addReservation(ReservationDTO reservationDTO){
        return Response.<ReservationDTO>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()
        ).data((service.addReservation(reservationDTO))).build();
    }
}
