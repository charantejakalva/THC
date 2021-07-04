package org.example.repository;

import org.example.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, String> {

    public Page<Restaurant> findAll(Pageable pageDetails);
}
