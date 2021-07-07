package org.example.service;

import org.example.dto.OpenHoursDTO;
import org.example.dto.ReservationDTO;

import java.util.List;

public interface OpenHourService {
    OpenHoursDTO addOpenHour(String restaurantId, OpenHoursDTO openHoursDTO);
    List<OpenHoursDTO> getAllOpenHour(int page, int size);
    List<OpenHoursDTO> getOpenHourOfRestaurant(String id, int page, int size);
    OpenHoursDTO updateOpenHour(OpenHoursDTO openHoursDTO);
    String deleteOpenHour(OpenHoursDTO openHoursDTO);
    OpenHoursDTO getOpenHourById(String id);
}
