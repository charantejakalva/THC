package org.example.service;

import org.example.dto.ReservationDTO;
import org.example.dto.RestaurantDTO;
import org.example.exception.ReservationServiceException;
import org.example.exception.RestaurantServiceException;
import org.example.model.Reservation;
import org.example.model.Restaurant;
import org.example.repository.ReservationRepository;
import org.example.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImplement implements ReservationService {

    private final ReservationRepository reservationRepository;
    private  final RestaurantRepository restaurantRepository;
    public ReservationServiceImplement(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository){
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ReservationDTO addReservation(String restaurantId,ReservationDTO reservationDTO) {
        try {
//            ReservationDTO reservationDTO;

            Reservation reservation = convertDTOtoEntity(reservationDTO);
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
            List<Reservation> reservationList = restaurant.getReservations();
            reservationList.add(reservation);
            restaurant.setReservations(reservationList);
//            String reservationId = reservation.getReservationId();
            Restaurant responseEntity = restaurantRepository.save(restaurant);
            Optional<Reservation> responseReservation = responseEntity.getReservations().stream().
                    filter(p -> p.getCustomerEmail().equals(reservation.getCustomerEmail()) &&
                            p.getReservationStartTime().equals(reservation.getReservationStartTime()) &&
                            p.getCustomerMobile().equals(reservation.getCustomerMobile())
                            ).
                    findFirst();
            return convertEntitytoDTO(responseReservation.get());
        }
        catch (Exception e){
            throw  new ReservationServiceException(e.getMessage());
        }
    }

    private ReservationDTO convertEntitytoDTO(Reservation reservation) {
        ReservationDTO reservationDto = new ReservationDTO();
        BeanUtils.copyProperties(reservation, reservationDto);
        return reservationDto;
    }
    private Reservation convertDTOtoEntity(ReservationDTO reservationDTO){
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO,reservation);
        return reservation;
    }
    @Override
    public List<ReservationDTO> getReservations(String restaurantId, int page, int size) {
        try{
//            Pageable pageDetails = PageRequest.of(page, size);
//            List<Reservation> reservation =  (List<Reservation>) reservationRepository.findAll();
//            List<Reservation> reservation =  (List<Reservation>) reservationRepository.findAll(pageDetails).getContent();
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

            List<Reservation> reservation = restaurant.getReservations();
            List<ReservationDTO> reservationDTOList =  new ArrayList<>();
            int last = Math.min(reservation.size(), (page)*(size)) ;
            for (int i =((page-1)*size); i < last;i++){
                    reservationDTOList.add(convertEntitytoDTO(reservation.get(i)));
            }
            return reservationDTOList;
        }
        catch (Exception e){
            throw new ReservationServiceException("Internal Server Error");
        }
    }

    @Override
    public  List<ReservationDTO> getAllReservations(int page, int size){
        try{
            Pageable pageDetails = PageRequest.of(page, size);
            List<Reservation> reservation =  (List<Reservation>) reservationRepository.findAll(pageDetails).getContent();
            List<ReservationDTO> reservationDTOList =  new ArrayList<>();
//            int last = Math.min(reservation.size(), (page+1)*(size)) ;
            for (int i =0; i < reservation.size();i++){
                    reservationDTOList.add(convertEntitytoDTO(reservation.get(i)));
            }
            return reservationDTOList;
        }
        catch (Exception e){
            throw new ReservationServiceException("Internal Server Error");
        }
    }
    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {
        if(reservationRepository.findById(reservationDTO.getReservationId()).isPresent()){
            try{

            Reservation reservation = new Reservation();
            reservation.setReservationId(reservationDTO.getReservationId());
            reservation.setCustomerName(reservationDTO.getCustomerName());
            reservation.setCustomerEmail(reservationDTO.getCustomerEmail());
            reservation.setCustomerMobile(reservationDTO.getCustomerMobile());
            reservation.setReservationStartTime(reservationDTO.getReservationStartTime());

            Reservation responseReservation = reservationRepository.save(reservation);
            return convertEntitytoDTO(responseReservation);
            }
            catch(Exception e){
                throw  new ReservationServiceException("Cannot Update Reservation");
            }

        }
        else{
            return null;
        }
    }

    @Override
    public String deleteReservation(ReservationDTO reservationDTO) {
       try{
           Reservation reservation = convertDTOtoEntity(reservationDTO);
            reservationRepository.delete(reservation);
           return "Deleted Successfully";
       }
       catch (Exception e){
           throw new ReservationServiceException("Delete Failed");
       }
    }

    @Override
    public ReservationDTO getReservationById(String id) {
        try {
            if(reservationRepository.findById(id).isPresent()) {
                return convertEntitytoDTO(reservationRepository.findById(id).get());
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            throw new ReservationServiceException("Internal Server Error");
        }
    }
}
