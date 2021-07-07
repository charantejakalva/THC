package org.example.service;

import org.apache.kafka.common.protocol.types.Field;
import org.example.dto.MenuDTO;
import org.example.dto.OpenHoursDTO;

import java.util.List;

public interface MenuService {
    MenuDTO addMenu(String restaurantId, MenuDTO menuDTO);

    List<MenuDTO> getMenuOfRestaurant(String id, int page, int size);

    MenuDTO updateMenu(MenuDTO menuDTO);

    String deleteMenu(MenuDTO menuDTO);

    MenuDTO getMenuById(String id);
}
