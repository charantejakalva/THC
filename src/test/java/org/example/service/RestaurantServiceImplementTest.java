package org.example.service;

import org.example.controller.RestaurantController;
import org.example.dto.MenuDTO;
import org.example.dto.OpenHoursDTO;
import org.example.dto.ReservationDTO;
import org.example.dto.RestaurantDTO;
import org.example.exception.RestaurantServiceException;
import org.example.model.Interceptor;
import org.example.model.Menu;
import org.example.model.Reservation;
import org.example.model.Restaurant;
import org.example.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplementTest {

    @Mock
    private RestaurantServiceImplement restaurantServiceImplement;
    private RestaurantController restaurantController;
    @BeforeEach
    void setUp() {
        restaurantController = new RestaurantController(restaurantServiceImplement);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRestaurant() {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurantDTOList.add(createData());
        doReturn(restaurantDTOList).when(restaurantServiceImplement).getRestaurant(any(),any());
        Response<List<RestaurantDTO>> res = restaurantController.getRestaurants(0,1);
        Assertions.assertEquals(res.getData().get(0).getRestaurantId(),createData().getRestaurantId());

//        Assertions.assertEquals(res.getData() ,createData().getRestaurantName());
    }

    @Test
    void addRestaurant() {
        doReturn(createData()).when(restaurantServiceImplement).addRestaurant(any());

        Response<RestaurantDTO> res = restaurantController.addRestaurant(createData());
        Assertions.assertEquals(res.getData().getRestaurantId(),createData().getRestaurantId());
        Assertions.assertEquals(res.getData().getRestaurantName(),createData().getRestaurantName());
//        Assertions.assertEquals(res.getData().getMenu(),createData().getMenu());

    }

    @Test
    void getRestaurantById() {
        doReturn(Optional.of(createData())).when(restaurantServiceImplement).getRestaurantById(any());

        Response<Optional<RestaurantDTO>> res = restaurantController.getRestaurantById(createData().getRestaurantId());
        Assertions.assertEquals(res.getData().get().getRestaurantId(),createData().getRestaurantId());
        Assertions.assertEquals(res.getData().get().getRestaurantName(),createData().getRestaurantName());

    }
    @Test
    void getRestaurantById2() {
        doReturn(null).when(restaurantServiceImplement).getRestaurantById(any());
        Response<Optional<RestaurantDTO>> res = restaurantController.getRestaurantById("char");
        Assertions.assertNull(res.getData());
//        Assertions.assertThrows(RestaurantServiceException.class, ()->
//        { restaurantController.getRestaurantById("char"); });
    }
    @Test
    void updateRestaurant() {
        doReturn(createData()).when(restaurantServiceImplement).updateRestaurant(any());
        Response<RestaurantDTO> res = restaurantController.updateRestaurant(createData());
        Assertions.assertEquals(res.getData().getRestaurantId(),createData().getRestaurantId());
        Assertions.assertEquals(res.getData().getRestaurantName(),createData().getRestaurantName());
    }
    @Test
    void updateRestaurant2() {
        doReturn(null).when(restaurantServiceImplement).updateRestaurant(any());
        RestaurantDTO restaurantDTO = createData();
        restaurantDTO.setRestaurantId("Char");
        Response<RestaurantDTO> res = restaurantController.updateRestaurant(restaurantDTO);
        Assertions.assertNull(res.getData());
    }
    @Test
    void deleteRestaurant() {
        doReturn("Deleted Successfully").when(restaurantServiceImplement).deleteRestaurant(any());
        Response<String> res = restaurantController.deleteRestaurant(createData().getRestaurantId());
        Assertions.assertEquals(res.getData(), "Deleted Successfully");
    }
    @Test
    void deleteRestaurant2() {
        doReturn("Problem in Deletion").when(restaurantServiceImplement).deleteRestaurant(any());
        try {
        Response<String> res = restaurantController.deleteRestaurant("char");
        System.out.println(res);
//        Assertions.assertEquals(res.getData(), "Deleted Successfully");
//            fail("Exception not thrown");
        } catch (RestaurantServiceException e) {
            assertEquals("Problem in Deletion", e.getMessage());
        }
    }

    @Test
    void createRandomRestaurant() {
    }

    RestaurantDTO createData(){
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        restaurantDTO.setRestaurantId("R1");
        restaurantDTO.setRestaurantName("THC1");
        restaurantDTO.setLocation("Texas");
        restaurantDTO.setPhoneNumber("1546846542");

        List<MenuDTO> menuList = new ArrayList<>();
        MenuDTO menu = new MenuDTO();
        menu.setMenuId("12");
        menu.setMenuItems(null);
        menuList.add(menu);

        List<ReservationDTO> reservationList = new ArrayList<>();
        ReservationDTO reservation = new ReservationDTO();
        reservation.setReservationId("1");
        reservation.setCustomerName("Charan");
        reservation.setCustomerMobile("7169397858");
        reservation.setCustomerEmail("charante@buffalo.edu");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        reservation.setReservationStartTime(timestamp);
        reservationList.add(reservation);

        List<OpenHoursDTO> openHoursDTOList  = new ArrayList<>();
        OpenHoursDTO openHoursDTO = new OpenHoursDTO();
        openHoursDTO.setDay("Monday");
        openHoursDTO.setId("1");
        openHoursDTO.setTiming("5pm to 10pm");

        openHoursDTOList.add(openHoursDTO);

        restaurantDTO.setMenu(menuList);
        restaurantDTO.setReservations(reservationList);
        restaurantDTO.setOpenHours(openHoursDTOList);
        return restaurantDTO;

    }
}