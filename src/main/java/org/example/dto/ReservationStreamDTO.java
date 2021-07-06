package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationStreamDTO {

    private int restaurantId;

    private ReservationDTO reservationDTO;
}
