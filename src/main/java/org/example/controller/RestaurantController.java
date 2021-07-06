package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.dto.RestaurantDTO;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.response.StatusMessage;
import org.example.service.RestaurantServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantController {

    private final RestaurantServiceImplement service;


    public RestaurantController(RestaurantServiceImplement service){
        this.service = service;
    }

    @ApiOperation(value = "Get all Restaurants", notes = "Will return all the details about all the restaurants")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "No Restaurants Found"),
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping("/restaurant/{page}/{size}")
    public Response<List<RestaurantDTO>> getRestaurants(@PathVariable  Integer page, @PathVariable Integer size){
        return Response.<List<RestaurantDTO>>builder().meta(ResponseMetadata.builder()
                                                    .statusCode(200)
                                                    .statusMessage(StatusMessage.SUCCESS.name())
                                                    .build())
                .data((service.getRestaurant(page,size))).build();
    }


    @ApiOperation(value = "Add a restaurant", notes = "Provide needed details to add a restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200, message = "Successful")
    })
    @PostMapping("/restaurant")
    public Response<RestaurantDTO> addRestaurant(RestaurantDTO restaurantDTO ){
        return Response.<RestaurantDTO>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(StatusMessage.SUCCESS.name())
                .build())
                .data((service.addRestaurant(restaurantDTO))).build();
    }


    @GetMapping("/restaurant/{id}")
    @ApiOperation(value = "Get Restaurant By Id.", notes = "Pass the id of the restaurant that you want to see details about.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<Optional<RestaurantDTO>> getRestaurantById(@PathVariable int id){

        if( service.getRestaurantById(id) == null){
            return  Response.<Optional<RestaurantDTO>>builder().meta(ResponseMetadata.builder()
                    .statusCode(404)
                    .statusMessage(HttpStatus.NOT_FOUND.toString()).build()
            ).data(null).build();
        }
        else {
            return Response.<Optional<RestaurantDTO>>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.getRestaurantById(id))).build();
        }
    }
    @PutMapping("/restaurant/update")
    @ApiOperation(value = "Update any field in Restaurant by passing the updated information. ")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<RestaurantDTO> updateRestaurant(@RequestBody RestaurantDTO restaurantDTO){

            return Response.<RestaurantDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.updateRestaurant(restaurantDTO))).build();

    }

    @DeleteMapping("/restaurant/delete/{id}")
    @ApiOperation(value = "Delete Restaurant based on id ")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<String> deleteRestaurant(@PathVariable int id){
        
        return Response.<String>builder().meta(ResponseMetadata.builder()
        .statusCode(200)
        .statusMessage(HttpStatus.OK.toString()).build())
                .data((service.deleteRestaurant(id))).build();
    }

    @ApiOperation(value = "Generate random Restaurant data", notes = "Will generate the random data for restaurants")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200, message = "Successful")
    })
    @PostMapping("/random/{num}")
    public Response<String> generateData(@ApiParam(required = true,defaultValue = "1") @PathVariable int num){
        return Response.<String>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.CREATED.toString())
                .build())
                .data((service.createRandomRestaurant(num))).build();
    }
}
