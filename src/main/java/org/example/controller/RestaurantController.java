package org.example.controller;

import org.example.dto.RestaurantDTO;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.response.StatusMessage;
import org.example.service.RestaurantServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    private RestaurantServiceImplement service;


    public RestaurantController(RestaurantServiceImplement service){
        this.service = service;
    }

    @GetMapping("/restaurants")
    public Response<RestaurantDTO> getRestaurants(){

        return Response.<RestaurantDTO>builder().meta(ResponseMetadata.builder()
                                                    .statusCode(200)
                                                    .statusMessage(StatusMessage.SUCCESS.name())
                                                    .build())
                .data((service.getRestaurant())).build();
    }

    @PostMapping("/random/{num}")
    public Response<String> generateData(@PathVariable int num){
        return Response.<String>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.CREATED.toString())
                .build())
                .data((service.createRandomRestaurant(num))).build();
    }
}
