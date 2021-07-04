package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationId;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private Timestamp reservationStartTime;

//    @ManyToOne( fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "restaurantId",nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
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
