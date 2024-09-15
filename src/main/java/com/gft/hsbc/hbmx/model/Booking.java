package com.gft.hsbc.hbmx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;

@Data
@NoArgsConstructor
public class Booking implements Serializable {

    private String customerName;
    private String tableSize;
    private String date;
    private Time time;

}
