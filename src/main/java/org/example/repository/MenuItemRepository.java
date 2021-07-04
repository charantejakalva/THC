package org.example.repository;

import org.example.model.MenuItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, String> {
}
