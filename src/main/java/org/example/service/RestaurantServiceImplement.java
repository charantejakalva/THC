package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.dto.MenuDTO;
import org.example.dto.OpenHoursDTO;
import org.example.dto.ReservationDTO;
import org.example.dto.RestaurantDTO;
import org.example.exception.RestaurantServiceException;
import org.example.model.*;
import org.example.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
@Log4j2
public class RestaurantServiceImplement implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;
    public  RestaurantServiceImplement(RestaurantRepository restaurantRepository){
        this.restaurantRepository=restaurantRepository;
    }
    public RestaurantServiceImplement(){}
    public List<RestaurantDTO> getRestaurant(Integer page, Integer size) {
        try {
            Pageable pageDetails = PageRequest.of(page, size);
            List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll(pageDetails).getContent();
            List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
            for (int i = 0; i < restaurants.size(); i++) {
                restaurantDTOList.add(convertEntitytoDTO(restaurants.get(i)));
            }
            log.info("The number of Restaurants returned are {}", restaurantDTOList.size());

            return restaurantDTOList;
        }
        catch (Exception e){
            throw new RestaurantServiceException("Error in getting Restaurants");
        }
    }

    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO ){

        try{
            RestaurantDTO responseDTO;
            Restaurant restaurant = convertDTOtoEntity(restaurantDTO);
            Restaurant responseEntity = restaurantRepository.save(restaurant);
            responseDTO = convertEntitytoDTO(responseEntity);
            log.info("The response entity obtained after adding the restaurant is {}", responseEntity);
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
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        BeanUtils.copyProperties(restaurant, restaurantDTO);

        List<ReservationDTO> reservations =  MapperUtil.mapList(restaurant.getReservations(), ReservationDTO.class);
        restaurantDTO.setReservations(reservations);
        List<OpenHoursDTO> openHours =  MapperUtil.mapList(restaurant.getOpenHours(), OpenHoursDTO.class);
        restaurantDTO.setOpenHours(openHours);
        List<MenuDTO> menus =  MapperUtil.mapList(restaurant.getMenu(), MenuDTO.class);
        restaurantDTO.setMenu(menus);

        return restaurantDTO;
    }
    private Restaurant convertDTOtoEntity(RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDTO,restaurant);
        List<Reservation> reservations =  MapperUtil.mapList(restaurantDTO.getReservations(), Reservation.class);
        restaurant.setReservations(reservations);
        List<OpenHours> openHours =  MapperUtil.mapList(restaurantDTO.getOpenHours(), OpenHours.class);
        restaurant.setOpenHours(openHours);
        List<Menu> menus =  MapperUtil.mapList(restaurantDTO.getMenu(), Menu.class);
        restaurant.setMenu(menus);


        return restaurant;

    }

    @Override
    public Optional<RestaurantDTO> getRestaurantById(String id) {
        try {
            if(restaurantRepository.findById(id).isPresent()) {
                Optional<Restaurant> restaurant = restaurantRepository.findById(id);
                RestaurantDTO restaurantDTO = convertEntitytoDTO(restaurant.get());
                log.info("Logging the Id of the Restaurant: {}", id);
                return Optional.of(restaurantDTO);
            }
            else{
                log.info("No Restaurant matched with the given ID");
                return null;
            }
        }
        catch (Exception e){
            throw new RestaurantServiceException("Restaurant Not Found");
        }
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO) {
        try {
            String id = restaurantDTO.getRestaurantId();

                Restaurant restaurant = restaurantRepository.findById(id).get();

                restaurant.setRestaurantName(restaurantDTO.getRestaurantName());

//                List<Reservation> reservations = new ArrayList<>();
//                BeanUtils.copyProperties(restaurantDTO.getReservations(), reservations);
//                restaurant.setReservations(reservations);
//                List<OpenHours> openHours = new ArrayList<>();
//                BeanUtils.copyProperties(restaurantDTO.getOpenHours(), openHours);
//                restaurant.setOpenHours(openHours);
//                List<Menu> menus = new ArrayList<>();
//                BeanUtils.copyProperties(restaurantDTO.getMenu(), menus);
//                restaurant.setMenu(menus);
                List<Reservation> reservations =  MapperUtil.mapList(restaurantDTO.getReservations(), Reservation.class);
                restaurant.setReservations(reservations);
                List<OpenHours> openHours =  MapperUtil.mapList(restaurantDTO.getOpenHours(), OpenHours.class);
                restaurant.setOpenHours(openHours);
                List<Menu> menus =  MapperUtil.mapList(restaurantDTO.getMenu(), Menu.class);
                restaurant.setMenu(menus);
                restaurant.setLocation(restaurantDTO.getLocation());
                restaurant.setPhoneNumber(restaurantDTO.getPhoneNumber());

                Restaurant responseRestaurant = restaurantRepository.save(restaurant);
            log.info("The response entity obtained after adding the restaurant is {}", responseRestaurant);

                return convertEntitytoDTO(responseRestaurant);

        }
        catch (Exception e){

            log.info("Logging Error: {}", e.getMessage());
            throw  new RestaurantServiceException("Cannot update the Restaurant");
        }

    }

    @Override
    public String deleteRestaurant(String id) {
        try {
            restaurantRepository.deleteById(id);
            log.info("Logging the Id of deleted Restaurant {}",id);
            return "Deleted Successfully";
        }
        catch (Exception ex){
            log.info("Logging Error {}",ex.getMessage());
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
