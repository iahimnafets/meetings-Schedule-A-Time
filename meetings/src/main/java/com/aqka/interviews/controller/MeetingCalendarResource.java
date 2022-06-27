
package com.aqka.interviews.controller;

import com.aqka.interviews.entities.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;


public interface MeetingCalendarResource {



    @Operation( summary =
            "    Add a working time for example:  0900 1730 or 0800 1630  means ( from 9:00 to 17:30 or from 8:00 to 16:30 )" )
    public ResponseEntity<Response> addWorkTime(@RequestParam( name = "timeStart" , required = true ) final String timeStart,
                                              @RequestParam( name = "timeEnd", required = true   ) final String timeEnd );


    @Operation( summary = " Book a new time in this format, example: \n" +
                          "dateRequest EmployeeId      : 2011-03-17 10:17:06 EMP001\n" +
                           "meetingStartTime Duration  : 2011-03-21 09:00 2 "  )
    public ResponseEntity<Response> addNewMeeting( @RequestBody final MeetingRequest meetingRequest  );

    @Operation( summary = " Find bookings by single day, example: \n" +
            "dateMeeting: 21-03-2011\n"  )
    public ResponseEntity<Response> getMeetingsByDate(
            @RequestParam( name = "dateMeeting",  required = false ) final String dateMeeting);

    @Operation( summary = " Find all bookings  "  )
    public ResponseEntity<Response> getAllMeetings() ;

}
