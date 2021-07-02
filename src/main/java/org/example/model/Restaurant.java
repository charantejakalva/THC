package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Restaurant {

    @Id
    private int restaurantId;

    private String location;

    private String restaurantName;

    private String phoneNumber;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Menu> menu;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Reservation> reservations;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<OpenHours> openHours;

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
//    public Set<OpenHours> getOpenHours() {
//        return openHours;
//    }
//
//    public void setOpenHours(Set<OpenHours> openHours) {
//        this.openHours = openHours;
//    }
}
