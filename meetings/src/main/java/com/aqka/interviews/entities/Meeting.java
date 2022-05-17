package com.aqka.interviews.entities;

import lombok.Data;

@Data
public class Meeting {

    private String timeStart;
    private String timeEnd;
    private String employeeId;

    public Meeting(String timeStart, String  timeEnd, String employeeId){
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.employeeId = employeeId;

    }
}
