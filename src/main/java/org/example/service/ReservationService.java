package org.example.service;

import org.example.dto.ReservationDTO;
import org.example.model.Interceptor;
import org.example.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    ReservationDTO addReservation(String restaurantId,ReservationDTO reservationDTO);
    List<ReservationDTO> getAllReservations(int page,int size);
    List<ReservationDTO> getReservations(String id, int page, int size);
    ReservationDTO updateReservation(ReservationDTO reservationDTO);
    String deleteReservation(String restaurantId, ReservationDTO reservationDTO);
    ReservationDTO getReservationById(String id);
}
