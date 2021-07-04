package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OpenHours {

    @Id
    private int id;

    private String day;
    private String timing;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Restaurant restaurant;
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
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
