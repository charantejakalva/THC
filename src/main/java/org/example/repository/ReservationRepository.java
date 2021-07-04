package org.example.repository;

import org.example.model.Reservation;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReservationRepository extends PagingAndSortingRepository<Reservation, String> {
}
