package org.example.repository;

import org.example.model.OpenHours;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OpenHoursRepository extends PagingAndSortingRepository<OpenHours, String> {
}
