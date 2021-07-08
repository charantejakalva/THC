package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.dto.OpenHoursDTO;
import org.example.exception.OpenHourServiceException;
import org.example.model.OpenHours;
import org.example.model.Reservation;
import org.example.model.Restaurant;
import org.example.repository.OpenHourRepository;
import org.example.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class OpenHourServiceImplement implements  OpenHourService{

    private final OpenHourRepository openHourRepository;
    private final RestaurantRepository restaurantRepository;
    public  OpenHourServiceImplement(OpenHourRepository openHourRepository,RestaurantRepository restaurantRepository){
        this.openHourRepository = openHourRepository;
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public OpenHoursDTO addOpenHour(String restaurantId, OpenHoursDTO openHoursDTO) {
       try{
           OpenHours openHours = convertDTOtoEntity(openHoursDTO);
           Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
           List<OpenHours> openHoursList = restaurant.getOpenHours();
           openHoursList.add(openHours);
           restaurant.setOpenHours(openHoursList);
           String openHoursId = openHours.getId();
           Restaurant responseEntity = restaurantRepository.save(restaurant);
           log.info("RestaurantId : {} and Open Hours data:{}",restaurantId,openHoursDTO);
           return convertEntitytoDTO(openHourRepository.findById(openHoursId).get());
       }
       catch (Exception e){
           throw new OpenHourServiceException("Cannot add OpenHours");
       }
    }

    @Override
    public List<OpenHoursDTO> getAllOpenHour(int page, int size) {
       try{
           Pageable pageDetails = PageRequest.of(page, size);
           List<OpenHours> openHoursList = openHourRepository.findAll(pageDetails).getContent();
           List<OpenHoursDTO> openHoursDTOList =  new ArrayList<>();
           for (int i =0; i < openHoursList.size();i++){
               openHoursDTOList.add(convertEntitytoDTO(openHoursList.get(i)));
           }
           return openHoursDTOList;
       }
       catch (Exception e){
           throw new OpenHourServiceException("Internal Server Error");
       }
    }

    @Override
    public List<OpenHoursDTO> getOpenHourOfRestaurant(String restaurantId, int page, int size) {
        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

            List<OpenHours> openHoursList = restaurant.getOpenHours();
            log.info("RestaurantId: {}", restaurantId);
            List<OpenHoursDTO> openHoursDTOList = new ArrayList<>();
            int last = Math.min(openHoursList.size(), (page) * (size));
            for (int i = ((page - 1) * size); i < last; i++) {
                openHoursDTOList.add(convertEntitytoDTO(openHoursList.get(i)));

            }
            return openHoursDTOList;
        }
        catch (Exception e){
            throw new OpenHourServiceException("Internal Server Error");
        }
    }

    @Override
    public OpenHoursDTO updateOpenHour(OpenHoursDTO openHoursDTO) {
        if(openHourRepository.findById(openHoursDTO.getId()).isPresent()) {
            try {
                OpenHours openHours = new OpenHours(); //openHourRepository.findById(Integer.toString(openHoursDTO.getId())).get();
                openHours.setDay(openHoursDTO.getDay());
                openHours.setTiming(openHoursDTO.getTiming());

                OpenHours response = openHourRepository.save(openHours);
                OpenHoursDTO responseDTO = convertEntitytoDTO(response);
                log.info("Updated OpenHours data:{}",responseDTO);
                return responseDTO;

            } catch (Exception e) {
                throw new OpenHourServiceException("Cannot Update OpenHours, Error Occurred");
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String deleteOpenHour(OpenHoursDTO openHoursDTO) {
        try{
            OpenHours openHours = convertDTOtoEntity(openHoursDTO);
            openHourRepository.delete(openHours);
            return "Deleted Successfully";
        }
        catch (Exception e){
            throw new OpenHourServiceException("Delete Failed");
        }
    }

    @Override
    public OpenHoursDTO getOpenHourById(String id) {
        try {
            if(openHourRepository.findById(id).isPresent()) {
                return convertEntitytoDTO(openHourRepository.findById(id).get());
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            throw new OpenHourServiceException("Internal Server Error");
        }
    }

    private OpenHoursDTO convertEntitytoDTO(OpenHours reservation) {
        OpenHoursDTO openHoursDTO = new OpenHoursDTO();
        BeanUtils.copyProperties(reservation, openHoursDTO);
        return openHoursDTO;
    }
    private OpenHours convertDTOtoEntity(OpenHoursDTO openHoursDTO){
        OpenHours openHours = new OpenHours();
        BeanUtils.copyProperties(openHoursDTO,openHours);

        return openHours;

    }
}
