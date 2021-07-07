package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.dto.OpenHoursDTO;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.service.OpenHourServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OpenHourController {
    private final OpenHourServiceImplement service;

    public OpenHourController(OpenHourServiceImplement service){
        this.service = service;
    }

    @GetMapping("/restaurant/{id}/openhour/{page}/{size}")
    @ApiOperation(value = "Get Open Hours from a particular restaurant",
            notes = "Provide needed values to get Open Hours of the restaurant.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<OpenHoursDTO>> getOpenHour(@PathVariable(value = "id") String restaurantId,
                                                          @PathVariable(value = "page") int page,
                                                          @PathVariable(value = "size") int size){
        return  Response.<List<OpenHoursDTO>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()

        ).data(service.getOpenHourOfRestaurant(restaurantId,page,size)).build();
    }

    @GetMapping("/restaurant/openhour/{page}/{size}")
    @ApiOperation(value = "Get Open Hours from all restaurants",
            notes = "Provide needed values to get all Open Hours from all restaurants.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<OpenHoursDTO>> getAllReservations(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size){
        return  Response.<List<OpenHoursDTO>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()

        ).data(service.getAllOpenHour(page,size)).build();
    }

    @GetMapping("/restaurant/openhour/{id}")
    @ApiOperation(value = "Get Open Hour based on Open Hour id",
            notes = "Provide needed values to get the Open Hour details based on open hour Id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<OpenHoursDTO> getOpenHourById(@PathVariable String id){

        if( service.getOpenHourById(id) == null){
            return  Response.<OpenHoursDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(404)
                    .statusMessage(HttpStatus.NOT_FOUND.toString()).build()
            ).data(null).build();
        }
        else {
            return Response.<OpenHoursDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.getOpenHourById(id))).build();
        }
    }

    @PostMapping("/restaurant/{id}/openhour")
    @ApiOperation(value = "Add a Open Hour details at the needed restaurant",
            notes = "Provide needed values to Add the Open Hour Details to a particular Restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<OpenHoursDTO> addReservation(@PathVariable(value = "id") String restaurantId,
                                                 @RequestBody OpenHoursDTO openHoursDTO){
        return Response.<OpenHoursDTO>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()
        ).data((service.addOpenHour(restaurantId,openHoursDTO))).build();
    }

    @PutMapping("/restaurant/{id}/openhour")
    @ApiOperation(value = "Update a Open Hour detail at the needed restaurant",
            notes = "Provide restaurant Id and Open Hour details to update the Open Hour at the restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant or Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<OpenHoursDTO> updateOpenHour(@RequestBody OpenHoursDTO openHoursDTO){

        if( service.updateOpenHour(openHoursDTO) == null){
            return  Response.< OpenHoursDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(404)
                    .statusMessage(HttpStatus.NOT_FOUND.toString()).build()
            ).data(null).build();
        }
        else {
            return Response.<OpenHoursDTO>builder().meta(ResponseMetadata.builder()
                    .statusCode(200)
                    .statusMessage(HttpStatus.OK.toString()).build()
            ).data((service.updateOpenHour(openHoursDTO))).build();
        }

    }


    @DeleteMapping("/restaurant/{id}/openhour")
    @ApiOperation(value = "Delete a Open Hour at the needed restaurant",
            notes = "Provide restaurant Id and Open Hour details to delete the Open Hour at the restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant or Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public  Response<String> deleteOpenHour(@RequestBody OpenHoursDTO openHoursDTO){

        return Response.<String>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()
        ).data((service.deleteOpenHour(openHoursDTO))).build();
    }
}
