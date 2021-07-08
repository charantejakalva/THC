package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.dto.MenuDTO;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.service.MenuServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    private  final MenuServiceImplement service;

    public MenuController(MenuServiceImplement service){
        this.service = service;
    }

    @GetMapping("/restaurant/{id}/menu/{page}/{size}")
    @ApiOperation(value = "Get Menus from a particular restaurant",
            notes = "Provide needed values to get all the Menus present in the restaurant.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<MenuDTO>> getMenuOfRestaurant(@PathVariable(value = "id") String restaurantId,
                                           @PathVariable(value = "page") int page,
                                           @PathVariable(value = "size") int size){

        return  Response.<List<MenuDTO>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()
        ).data(service.getMenuOfRestaurant(restaurantId,page,size)).build();
    }

    @GetMapping("/restaurant/menu/{id}")
    @ApiOperation(value = "Get Menu based on Menu id",
            notes = "Provide needed values to get the Menu details based on Id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<MenuDTO> getMenuById(@PathVariable String id){

        if( service.getMenuById(id) == null){
            return  Response.< MenuDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(404)
                    .statusMessage(HttpStatus.NOT_FOUND.toString()).build()
            ).data(null).build();
        }
        else {
            return Response.<MenuDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.getMenuById(id))).build();
        }
    }

    @PostMapping("/restaurant/{id}/menu")
    @ApiOperation(value = "Add a Menu to the restaurant",
            notes = "Provide needed values to Add the Menu at a Restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<MenuDTO> addReservation(@ApiParam(required = true,value = "RestaurantId") @PathVariable(value = "id") String restaurantId,
                                                   @RequestBody MenuDTO menuDTO){


        return Response.<MenuDTO>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()
        ).data((service.addMenu(restaurantId,menuDTO))).build();
    }

    @PutMapping("/restaurant/{id}/menu")
    @ApiOperation(value = "Update a Menu at the needed restaurant",
            notes = "Provide restaurant Id and Menu details to update the Menu at the restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant or Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<MenuDTO> updateMenu(@ApiParam(required = true,value = "RestaurantId") @PathVariable(value = "id") String id, @RequestBody MenuDTO menuDTO){

        if( service.updateMenu(menuDTO) == null){
            return  Response.<MenuDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(404)
                    .statusMessage(HttpStatus.NOT_FOUND.toString()).build()
            ).data(null).build();
        }
        else {
            return Response.<MenuDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.updateMenu(menuDTO))).build();
        }

    }

    @DeleteMapping("/restaurant/{id}/menu")
    @ApiOperation(value = "Delete a Menu at the needed restaurant",
            notes = "Provide restaurant Id and Menu details to delete the Menu at the restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant or Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public  Response<String> deleteMenu(@ApiParam(required = true,value = "RestaurantId") @PathVariable(value = "id") String id, @RequestBody MenuDTO menuDTO){

        return Response.<String>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()
        ).data((service.deleteMenu(menuDTO))).build();

    }

}
