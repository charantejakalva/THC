package org.example.service;

import org.example.dto.RestaurantDTO;
import org.example.exception.RestaurantServiceException;
import org.example.model.*;
import org.example.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamUtils;
import uk.co.jemos.podam.typeManufacturers.IntTypeManufacturerImpl;
import uk.co.jemos.podam.typeManufacturers.TypeManufacturer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImplement implements RestaurantService{
    private final RestaurantRepository restaurantRepository;
    public  RestaurantServiceImplement(RestaurantRepository restaurantRepository){
        this.restaurantRepository=restaurantRepository;
    }

    public List<RestaurantDTO> getRestaurant(Integer page, Integer size) {
        Pageable pageDetails = PageRequest.of(page, size);

        List<Restaurant> restaurants =  (List<Restaurant>) restaurantRepository.findAll(pageDetails).getContent();

        List<RestaurantDTO> restaurantDTOList =  new ArrayList<>();

        for (int i =0; i < restaurants.size();i++){
            restaurantDTOList.add(convertEntitytoDTO(restaurants.get(i)));
        }
//        if(restaurantDTOList.size() > 0){
//            return restaurantDTOList;
//        }
//        else
//            return null;

        return restaurantDTOList;
    }

    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO ){

        try{
            RestaurantDTO responseDTO;
            Restaurant restaurant = convertDTOtoEntity(restaurantDTO);
            Restaurant responseEntity = restaurantRepository.save(restaurant);
            responseDTO = convertEntitytoDTO(responseEntity);
            return responseDTO;
//            Restaurant entity = new Restaurant();
//            BeanUtils.copyProperties(restaurantDTO,entity);
//            List<Menu> menuList = new ArrayList<>(restaurantDTO.getMenu().size());
//            List<Reservation> reservationList  = new ArrayList<>(restaurantDTO.getReservations().size());
//            List<OpenHours> openHoursList = new ArrayList<>(restaurantDTO.getOpenHours().size());
//            for (int i = 0; i < restaurantDTO.getMenu().size(); i++){
//                Menu menu = new Menu();
//                BeanUtils.copyProperties(restaurantDTO.getMenu().get(i), menu);
//                menu.setRestaurant(entity);
//                menuList.add(menu);
//            }
//            for (int i =0; i < restaurantDTO.getReservations().size();i++){
//                Reservation reservation = new Reservation();
//                BeanUtils.copyProperties(restaurantDTO.getReservations().get(i), reservation);
//                reservation.setRestaurant(entity);
//                reservationList.add(reservation);
//            }
//            for (int i =0; i < restaurantDTO.getOpenHours().size(); i++){
//                OpenHours openHours =  new OpenHours();
//                BeanUtils.copyProperties(restaurantDTO.getOpenHours().get(i), openHours);
//                openHours.setRestaurant(entity);
//                openHoursList.add(openHours);
//            }
//            entity.setMenu(menuList);
//            entity.setReservations(reservationList);
//            entity.setOpenHours(openHoursList);
//
//            Restaurant responseEntity = restaurantRepository.save(entity);
//            RestaurantDTO responseDTO = new RestaurantDTO();
//            responseDTO = convertEntitytoDTO(responseEntity);
//        return responseDTO;



        }
        catch (Exception ex){
            throw new RestaurantServiceException("Internal Server Error");
        }

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

    @Override
    public Optional<RestaurantDTO> getRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id.toString());
        RestaurantDTO restaurantDTO = convertEntitytoDTO(restaurant.get());
        return  Optional.of(restaurantDTO);
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO) {
        try {
            int id = restaurantDTO.getRestaurantId();

            if (restaurantRepository.findById(Integer.toString(id)).isPresent()) {
                Restaurant restaurant = restaurantRepository.findById(Integer.toString(id)).get();

                restaurant.setRestaurantName(restaurantDTO.getRestaurantName());
                restaurant.setReservations(restaurantDTO.getReservations());
                restaurant.setOpenHours(restaurantDTO.getOpenHours());
                restaurant.setLocation(restaurantDTO.getLocation());
                restaurant.setMenu(restaurantDTO.getMenu());
                restaurant.setPhoneNumber(restaurantDTO.getPhoneNumber());

                Restaurant responseRestaurant = restaurantRepository.save(restaurant);
                return convertEntitytoDTO(responseRestaurant);
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            throw  new RestaurantServiceException("Cannot update the Restaurant");
        }

    }

    @Override
    public String deleteRestaurant(Integer id) {
        try {

            restaurantRepository.deleteById(id.toString());
            return "Deleted Successfully";
        }
        catch (Exception ex){
            throw  new RestaurantServiceException("Problem in Deletion");
        }

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
//            RestaurantDTO restaurantDTO = convertEntitytoDTO(restaurant);
//            this.addRestaurant(restaurantDTO);
            this.restaurantRepository.save(restaurant);
        }
        return "success";

    }
}
