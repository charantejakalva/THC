package org.example.controller;



import org.example.dto.ReservationDTO;
import org.example.dto.ReservationStreamDTO;
import org.example.exception.ReservationServiceException;
import org.example.service.kafka.ProducerService;
import org.springframework.web.bind.annotation.*;

///https://www.youtube.com/watch?v=EUzH9khPYgs&t=9s
//STEPS to start kafka on windows.
// 1.) .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
// 2.) .\bin\windows\kafka-server-start.bat .\config\server.properties
// Create Kafka Topic to send the data to.
// 3.) .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181--replication-factor 1 --partitions 1 --topic TOPIC_NAME



@RestController
public class KafkaController {

    private final ProducerService producerService;

    public KafkaController(ProducerService producerService){
        this.producerService = producerService;
    }

    @PostMapping(value = "/kafka/publish/restaurant/reservation")
    public String sendOrderToKafkaTopic( @RequestBody ReservationStreamDTO reservationStreamDTO){
        try {
            System.out.println("Inside the Kafka Controller");
            ReservationDTO reservationDTO = reservationStreamDTO.getReservationDTO();
//            int id = reservationStreamDTO.getRestaurantId();
//            int reservationId = reservationDTO.getReservationId();
//            reservationDTO.setReservationId(reservationId +((int) Instant.now().getEpochSecond()));

            producerService.sendReservationData(reservationStreamDTO);
            return "Reservation Received";

        }
        catch (Exception ex){
            throw new ReservationServiceException("Error while Reservation");
        }
    }


}
