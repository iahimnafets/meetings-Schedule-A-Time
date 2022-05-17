package com.aqka.interviews.entities;

import lombok.Data;

import java.util.Date;

@Data
public class MeetingDTO {

    private Date  meetingDate;
    private Date  timeStart;
    private Date  timeEnd;
    private String employeeId;

}
