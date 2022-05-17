package com.aqka.interviews.controller;


import com.aqka.interviews.entities.Meeting;
import com.aqka.interviews.entities.MeetingDTO;
import com.aqka.interviews.entities.MeetingRequest;
import com.aqka.interviews.entities.MeetingResponse;
import com.aqka.interviews.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping( "/api/meeting" )
@Slf4j
public class MeetingCalendarController implements MeetingCalendarResource
{


    private final MeetingService matchService;

    @Autowired
    public MeetingCalendarController(final MeetingService matchService )
    {
        this.matchService = matchService;
    }



    @Override
    @PutMapping ( value = "/add-work-time" )
    public ResponseEntity<String> addWorkTime(@RequestParam( name = "timeStart" , required = true ) final String timeStart,
                                              @RequestParam( name = "timeEnd", required = true   ) final String timeEnd )
    {
        matchService.addWorkTime( timeStart, timeEnd );
        return ResponseEntity.ok("Work Time added successfully" );
    }


    @Override
    @PutMapping( value = "/add-new-meeting" )
    public ResponseEntity<String> addNewMeeting( @RequestBody final MeetingRequest meetingRequest  )
    {
        matchService.addNewMeeting( meetingRequest );
        return ResponseEntity.ok("Meeting added correctly!");
    }



    @Override
    @GetMapping( value = "/get-meetings-by-date" )
    public ResponseEntity<Collection<Meeting>> getMeetingsByDate(
                       @RequestParam( name = "dateMeeting",  required = false ) final String dateMeeting) {
        Collection<Meeting> result = matchService.getMeetingsByDate ( dateMeeting );
        return ResponseEntity.ok(result );
    }

    @Override
    @GetMapping( value = "/get-all-meetings" )
    public ResponseEntity<Collection<MeetingResponse>> getAllMeetings() {
        Collection<MeetingResponse> result = matchService.getAllMeetings ( );
        return ResponseEntity.ok(result );
    }

}
