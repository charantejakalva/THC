package org.example.service;

import org.example.dto.RestaurantDTO;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    List<RestaurantDTO> getRestaurant(Integer page, Integer size);

    RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);

    Optional<RestaurantDTO> getRestaurantById(String id);

    RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO);

    String deleteRestaurant(String id);

    String createRandomRestaurant(int num);
}
