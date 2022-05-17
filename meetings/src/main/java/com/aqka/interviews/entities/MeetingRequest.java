package com.aqka.interviews.entities;

import lombok.Data;

@Data
public class MeetingRequest
{
    private final String dateRequestAndEmployeeId;
    private final String meetingStartTimeAndDuration;

}
