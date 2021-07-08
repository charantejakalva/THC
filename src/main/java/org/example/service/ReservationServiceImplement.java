package org.example.service;

import lombok.extern.log4j.Log4j2;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReservationServiceImplement implements ReservationService {

    private final ReservationRepository reservationRepository;
    private  final RestaurantRepository restaurantRepository;
    public ReservationServiceImplement(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository){
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public ReservationDTO addReservation(String restaurantId,ReservationDTO reservationDTO) {
        try {
//            ReservationDTO reservationDTO;

            Reservation reservation = convertDTOtoEntity(reservationDTO);
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
            List<Reservation> reservationList = restaurant.getReservations();
            reservationList.add(reservation);
            restaurant.setReservations(reservationList);
//            String reservationId = reservation.getReservationId();
            log.info("Adding Reservation data: {} to the RestaurantId: {}", reservation,restaurantId);
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
            log.info("Total number of reservations: {}",reservation.size());
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
            log.info("Number of Reservations: {}",reservation.size());
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
            log.info("Updated Reservation data: {}",responseReservation);
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
    public String deleteReservation(String restaurantId, ReservationDTO reservationDTO) {
       try{
            Reservation reservation = convertDTOtoEntity(reservationDTO);
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
//            restaurant.getReservations().remove(reservation);
            List<Reservation> reservationList = restaurant.getReservations();
            reservationList.removeIf(x -> x.getReservationId().equals(reservationDTO.getReservationId()));
            restaurant.setReservations(reservationList);
            restaurantRepository.save(restaurant);
            reservationRepository.delete(reservation);
            log.info("Deleted Reservation ID: {}",reservationDTO.getReservationId() );
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
                log.info("Reservation Requested is :{}", id);
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
