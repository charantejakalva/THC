package org.example.service;

import org.example.dto.MenuDTO;
import org.example.dto.OpenHoursDTO;

import java.util.List;

public interface MenuService {
    MenuDTO addMenu(int restaurantId, MenuDTO menuDTO);

    List<MenuDTO> getMenuOfRestaurant(int id, int page, int size);

    MenuDTO updateMenu(MenuDTO menuDTO);

    String deleteMenu(MenuDTO menuDTO);

    MenuDTO getMenuById(int id);
}
