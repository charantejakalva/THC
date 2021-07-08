package org.example.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReservationDTO;
import org.example.dto.ReservationStreamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class ProducerService {
//    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private final KafkaTemplate<String, ReservationStreamDTO> reservationStreamDTOKafkaTemplate;

//    @Value("${kafka.topic.json-demo.name}")
    @Value("${spring.kafka.topic.THC.name}")
    private String JSON_TOPIC;

    public ProducerService(  KafkaTemplate<String, ReservationStreamDTO> reservationStreamDTOKafkaTemplate) {

        this.reservationStreamDTOKafkaTemplate = reservationStreamDTOKafkaTemplate;
    }

    public void sendReservationData(ReservationStreamDTO reservationStreamDTO) {
        log.info(String.format("$$$$ => Producing message: %s", reservationStreamDTO));
        System.out.println("Inside the Producer Service before sending data to Topic");
        reservationStreamDTOKafkaTemplate.executeInTransaction(t -> {
            ListenableFuture<SendResult<String, ReservationStreamDTO>> future = t.send(JSON_TOPIC, reservationStreamDTO.getReservationDTO().getReservationId(), reservationStreamDTO);
            future.addCallback(new ListenableFutureCallback<SendResult<String, ReservationStreamDTO>>() {
                @Override
                public void onSuccess(SendResult<String, ReservationStreamDTO> result) {
                    log.info("Sent message=[ {} ] with offset=[ {} ] to the Topic", reservationStreamDTO, result.getRecordMetadata().offset());

                }

                @Override
                public void onFailure(Throwable ex) {
                    log.info("Unable to produce message=[ {} ] due to : {}", reservationStreamDTO, ex.getMessage());
                }

            });
            return true;
        });
    }

}
