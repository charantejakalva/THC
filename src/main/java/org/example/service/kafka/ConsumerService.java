package org.example.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReservationDTO;
import org.example.dto.ReservationStreamDTO;
import org.example.dto.RestaurantDTO;
import org.example.service.ReservationServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private ReservationServiceImplement reservationServiceImplement;



    @KafkaListener(containerFactory = "jsonKafkaListenerContainerFactory",
            topics = "${spring.kafka.topic.THC.name}",
            groupId = "${spring.kafka.topic.THC.groupId}")
    public void consumeReservationData( ReservationStreamDTO reservationStreamDTO) {
        System.out.println("Inside the Consumer Service");

        log.info("Consumed Message: {}, {}", reservationStreamDTO.getReservationDTO().getReservationId(), reservationStreamDTO.getRestaurantId());

//        int restaurantId = reservationStreamDTO.getRestaurantId();
//        ReservationDTO reservationDTO = reservationStreamDTO.getReservationDTO();
        reservationServiceImplement.addReservation(reservationStreamDTO.getRestaurantId(),reservationStreamDTO.getReservationDTO());


    }

}
