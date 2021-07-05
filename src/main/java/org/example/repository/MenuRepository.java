package org.example.repository;

import org.example.model.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuRepository extends PagingAndSortingRepository<Menu, String> {
}
