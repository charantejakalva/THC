package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Interceptor {

//    @Id
//    private  int id;
    @Id
    private Timestamp startTime;
    private Timestamp endTime;
    private String request;
    private long executionTime;
}
