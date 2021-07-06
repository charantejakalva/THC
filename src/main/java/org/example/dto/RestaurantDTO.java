package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Menu;
import org.example.model.OpenHours;
import org.example.model.Reservation;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RestaurantDTO {
    private int restaurantId;

    private String location;

    private String restaurantName;

    private String phoneNumber;

    private List<MenuDTO> menu;

    private List<ReservationDTO> reservations;

    private List<OpenHoursDTO> openHours;

//    public int getRestaurantId() {
//        return restaurantId;
//    }
//
//    public void setRestaurantId(int restaurantId) {
//        this.restaurantId = restaurantId;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getRestaurantName() {
//        return restaurantName;
//    }
//
//    public void setRestaurantName(String restaurantName) {
//        this.restaurantName = restaurantName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public Set<Menu> getMenu() {
//        return menu;
//    }
//
//    public void setMenu(Set<Menu> menu) {
//        this.menu = menu;
//    }
//
//    public Set<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public void setReservations(Set<Reservation> reservations) {
//        this.reservations = reservations;
//    }
//
//    public OpenHours getOpenHours() {
//        return openHours;
//    }
//
//    public void setOpenHours(OpenHours openHours) {
//        this.openHours = openHours;
//    }

}
