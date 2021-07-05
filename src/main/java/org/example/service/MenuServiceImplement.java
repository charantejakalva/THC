package org.example.service;

import org.example.dto.MenuDTO;
import org.example.exception.MenuServiceException;
import org.example.exception.ReservationServiceException;
import org.example.model.Menu;
import org.example.model.Restaurant;
import org.example.repository.MenuRepository;
import org.example.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImplement implements MenuService {
    private final MenuRepository menuRepository;
    private  final RestaurantRepository restaurantRepository;

    public MenuServiceImplement(MenuRepository menuRepository, RestaurantRepository restaurantRepository){
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public MenuDTO addMenu(int restaurantId, MenuDTO menuDTO) {
       try{
           MenuDTO responseDTO;
           Menu menu = convertDTOtoEntity(menuDTO);
           Menu responseEntity = menuRepository.save(menu);
           responseDTO = convertEntitytoDTO(responseEntity);
           return responseDTO;

       }
       catch (Exception e){
           throw new MenuServiceException("Internal Server Error");
       }
    }

    @Override
    public List<MenuDTO> getMenuOfRestaurant(int restaurantId, int page, int size) {
        try{
//            Pageable pageDetails = PageRequest.of(page, size);
              Restaurant restaurant = restaurantRepository.findById(Integer.toString(restaurantId)).get();
            List<Menu> menuList = restaurant.getMenu();
            List<MenuDTO> menuDTOList =  new ArrayList<>();
            int last = Math.min(menuList.size(), (page)*(size)) ;
            for (int i =((page-1)*size); i < last;i++){
                menuDTOList.add(convertEntitytoDTO(menuList.get(i)));
            }
            return menuDTOList;
        }
        catch (Exception e){
            throw new ReservationServiceException("Internal Server Error");
        }
    }

    @Override
    public MenuDTO updateMenu(MenuDTO menuDTO) {
        if(menuRepository.findById(Integer.toString(menuDTO.getMenuId())).isPresent()){
            try{
                Menu menu = new Menu();
                menu.setMenuId(menuDTO.getMenuId());
                menu.setMenuItems(menuDTO.getMenuItems());

                Menu menuResponse = menuRepository.save(menu);

                return convertEntitytoDTO(menuResponse);


            }
            catch (Exception e){
                throw new MenuServiceException("Internal server Error");
            }

        }
        else{
            return null;
        }
    }

    @Override
    public String deleteMenu(MenuDTO menuDTO) {
        try{
            Menu menu = convertDTOtoEntity(menuDTO);
            menuRepository.delete(menu);
            return "Deleted Successfully";
        }
        catch (Exception e){
            throw new ReservationServiceException("Delete Failed");
        }
    }

    @Override
    public MenuDTO getMenuById(int id) {try {
        if(menuRepository.findById(Integer.toString(id)).isPresent()) {
            return convertEntitytoDTO(menuRepository.findById(Integer.toString(id)).get());
        }
        else{
            return null;
        }
    }
    catch (Exception e){
        throw new ReservationServiceException("Internal Server Error");
    }
    }



    private MenuDTO convertEntitytoDTO(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        return menuDTO;
    }
    private Menu convertDTOtoEntity(MenuDTO menuDTO){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO,menu);

        return menu;

    }
}
