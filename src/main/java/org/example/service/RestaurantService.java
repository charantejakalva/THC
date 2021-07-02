package org.example.service;

import org.example.dto.RestaurantDTO;
import org.example.model.Restaurant;

public interface RestaurantService {

    RestaurantDTO getRestaurant();

    RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);

    String createRandomRestaurant(int num);
}
