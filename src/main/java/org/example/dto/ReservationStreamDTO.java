package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationStreamDTO {

    private String restaurantId;

    private ReservationDTO reservationDTO;
}
