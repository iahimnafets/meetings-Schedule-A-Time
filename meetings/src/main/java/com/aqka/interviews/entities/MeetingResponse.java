package com.aqka.interviews.entities;

import lombok.Data;

import java.util.List;

@Data
public class MeetingResponse {

    private String dateMeeting;
    private List<Meeting> listMeetings;

}
