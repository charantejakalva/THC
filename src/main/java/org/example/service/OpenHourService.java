package org.example.service;

import org.example.dto.OpenHoursDTO;
import org.example.dto.ReservationDTO;

import java.util.List;

public interface OpenHourService {
    OpenHoursDTO addOpenHour(int restaurantId, OpenHoursDTO openHoursDTO);
    List<OpenHoursDTO> getAllOpenHour(int page, int size);
    List<OpenHoursDTO> getOpenHourOfRestaurant(int id, int page, int size);
    OpenHoursDTO updateOpenHour(OpenHoursDTO openHoursDTO);
    String deleteOpenHour(OpenHoursDTO openHoursDTO);
    OpenHoursDTO getOpenHourById(int id);
}
