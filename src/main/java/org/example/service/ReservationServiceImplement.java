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

import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImplement implements ReservationService {

    private final ReservationRepository reservationRepository;
    private  final RestaurantRepository restaurantRepository;
    public ReservationServiceImplement(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository){
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        try {
//            ReservationDTO reservationDTO;
            Reservation reservation = convertDTOtoEntity(reservationDTO);
            Reservation responseEntity = reservationRepository.save(reservation);
            return convertEntitytoDTO(responseEntity);
        }
        catch (Exception e){
            throw  new ReservationServiceException("Cannot Add Reservation");
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
    public List<ReservationDTO> getReservations(int restaurantId, int page, int size) {
        try{
//            Pageable pageDetails = PageRequest.of(page, size);

//            List<Reservation> reservation =  (List<Reservation>) reservationRepository.findAll();
//            List<Reservation> reservation =  (List<Reservation>) reservationRepository.findAll(pageDetails).getContent();
            Restaurant restaurant = restaurantRepository.findById(Integer.toString(restaurantId)).get();

            List<Reservation> reservation = restaurant.getReservations();

            List<ReservationDTO> reservationDTOList =  new ArrayList<>();
            int last = Math.min(reservation.size(), (page+1)*(size)) ;
            for (int i =(page*size); i < last;i++){
                if(reservation.get(i).getReservationId() == restaurantId) {
                    reservationDTOList.add(convertEntitytoDTO(reservation.get(i)));
                }
            }
//            if(reservationDTOList.size() > 0){
//                return reservationDTOList.get(0);
//            }
//            else
//                return new ReservationDTO();
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
        if(reservationRepository.findById(Integer.toString(reservationDTO.getReservationId())).isPresent()){
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
    public ReservationDTO getReservationById(int id) {
        try {
            if(reservationRepository.findById(Integer.toString(id)).isPresent()) {
                return convertEntitytoDTO(reservationRepository.findById(Integer.toString(id)).get());
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
