package com.aqka.interviews.controller;


import com.aqka.interviews.entities.*;
import com.aqka.interviews.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;


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
    public ResponseEntity<Response> addWorkTime(@RequestParam( name = "timeStart" , required = true ) final String timeStart,
                                              @RequestParam( name = "timeEnd", required = true   ) final String timeEnd )
    {
        matchService.addWorkTime( timeStart, timeEnd );

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .data(Map.of("add-work-time", "Work Time added successfully" ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @Override
    @PutMapping( value = "/add-new-meeting" )
    public ResponseEntity<Response> addNewMeeting( @RequestBody final MeetingRequest meetingRequest  )
    {
        matchService.addNewMeeting( meetingRequest );

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .data(Map.of("add-new-meeting", "Meeting added correctly!" ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }



    @Override
    @GetMapping( value = "/get-meetings-by-date" )
    public ResponseEntity<Response> getMeetingsByDate(
                       @RequestParam( name = "dateMeeting",  required = false ) final String dateMeeting) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .data(Map.of("get-meetings-by-date", matchService.getMeetingsByDate ( dateMeeting ) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @Override
    @GetMapping( value = "/get-all-meetings" )
    public ResponseEntity<Response> getAllMeetings() {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .data(Map.of("get-all-meetings", matchService.getAllMeetings ( ) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
