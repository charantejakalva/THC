package org.example.service;

import org.example.dto.RestaurantDTO;
import org.example.model.Restaurant;
import org.example.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamUtils;
import uk.co.jemos.podam.typeManufacturers.IntTypeManufacturerImpl;
import uk.co.jemos.podam.typeManufacturers.TypeManufacturer;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImplement implements RestaurantService{
    private RestaurantRepository restaurantRepository;
    public  RestaurantServiceImplement(RestaurantRepository restaurantRepository){
        this.restaurantRepository=restaurantRepository;
    }

    public RestaurantDTO getRestaurant() {
         List<Restaurant> restaurants =  (List<Restaurant>) restaurantRepository.findAll();

        List<RestaurantDTO> restaurantDTOList =  new ArrayList<>();

        for (int i =0; i < restaurants.size();i++){
            restaurantDTOList.add(convertEntitytoDTO(restaurants.get(i)));
        }
        if(restaurantDTOList.size() > 0){
            return restaurantDTOList.get(0);
        }
        else
            return new RestaurantDTO();

//        return restaurantDTO;
    }

    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO ){

        RestaurantDTO responseDTO;
        Restaurant restaurant = convertDTOtoEntity(restaurantDTO);
        Restaurant responseEntity = restaurantRepository.save(restaurant);
        responseDTO = convertEntitytoDTO(responseEntity);
        return responseDTO;
    }

    private RestaurantDTO convertEntitytoDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDto = new RestaurantDTO();
        BeanUtils.copyProperties(restaurant, restaurantDto);
        return restaurantDto;
    }
    private Restaurant convertDTOtoEntity(RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDTO,restaurant);

        return restaurant;

    }

    public String createRandomRestaurant(int num){
        PodamFactory factory =  new PodamFactoryImpl();
        TypeManufacturer<Integer> manufacturer = new IntTypeManufacturerImpl(){

            @Override
            public Integer getInteger(AttributeMetadata attributeMetadata){
                if(attributeMetadata.getPojoClass().getName().equalsIgnoreCase("java.sql.Timestamp")){
                    return PodamUtils.getIntegerInRange(0,999);
                }
                else{
                    return super.getInteger(attributeMetadata);
                }
            }
        };
        factory.getStrategy().addOrReplaceTypeManufacturer(int.class,manufacturer);
        for(int i=0; i<num;i++){
            Restaurant restaurant = factory.manufacturePojoWithFullData(Restaurant.class);
            RestaurantDTO restaurantDTO = convertEntitytoDTO(restaurant);
            this.addRestaurant(restaurantDTO);
        }
        return "success";

    }
}
