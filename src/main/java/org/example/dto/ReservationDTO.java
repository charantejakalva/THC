package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Restaurant;

import java.sql.Timestamp;

@Getter
@Setter
public class ReservationDTO {
    private int reservationId;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private Timestamp reservationStartTime;
//    private Restaurant restaurant;

//    public int getReservationId() {
//        return reservationId;
//    }
//
//    public void setReservationId(int reservationId) {
//        this.reservationId = reservationId;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public String getCustomerMobile() {
//        return customerMobile;
//    }
//
//    public void setCustomerMobile(String customerMobile) {
//        this.customerMobile = customerMobile;
//    }
//
//    public String getCustomerEmail() {
//        return customerEmail;
//    }
//
//    public void setCustomerEmail(String customerEmail) {
//        this.customerEmail = customerEmail;
//    }
//
//    public Timestamp getReservationStartTime() {
//        return reservationStartTime;
//    }
//
//    public void setReservationStartTime(Timestamp reservationStartTime) {
//        this.reservationStartTime = reservationStartTime;
//    }
}
