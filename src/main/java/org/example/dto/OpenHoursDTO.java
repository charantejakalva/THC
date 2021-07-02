package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Restaurant;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class OpenHoursDTO {
    private int id;

    private Restaurant restaurant;
    private String day;
    private String timing;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Restaurant getRestaurant() {
//        return restaurant;
//    }
//
//    public void setRestaurant(Restaurant restaurant) {
//        this.restaurant = restaurant;
//    }
//
//    public String getDay() {
//        return day;
//    }
//
//    public void setDay(String day) {
//        this.day = day;
//    }
//
//    public String getTiming() {
//        return timing;
//    }
//
//    public void setTiming(String timing) {
//        this.timing = timing;
//    }
}
