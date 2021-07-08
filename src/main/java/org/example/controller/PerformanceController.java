package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.model.Interceptor;
import org.example.response.Response;
import org.example.response.ResponseMetadata;
import org.example.service.InterceptorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PerformanceController {
    private  final InterceptorService service;

    public PerformanceController(InterceptorService service){
        this.service = service;
    }

    @GetMapping("/performance/name/{name}")
    @ApiOperation(value = "Get the records of API performance filtering by Controller Name",
            notes = "Provide needed values to the performance of the end points of API and filter by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<Interceptor>> getPerformanceRecordsByName(@ApiParam(required = true,name = "RestaurantId", defaultValue = "Restaurant") @PathVariable(value = "name") String controllerName){

        return  Response.<List<Interceptor>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()

        ).data(service.getByName(controllerName)).build();
    }

    @GetMapping("/performance/date/{date}")
    @ApiOperation(value = "Get the records of API performance filtering by date",
            notes = "Provide needed values to the performance of the end points of API and filter by date." +
                    " Date Format should be YYYY-MM-DD")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Restaurant Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 200,message = "Successful")
    })
    public Response<List<Interceptor>> getPerformanceRecordsByDate(@PathVariable(value = "date") String date){

        return  Response.<List<Interceptor>>builder().meta(ResponseMetadata.builder()
                .statusCode(200)
                .statusMessage(HttpStatus.OK.toString()).build()

        ).data(service.getByDate(date)).build();
    }

}
