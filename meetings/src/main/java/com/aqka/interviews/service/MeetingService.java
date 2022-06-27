package com.aqka.interviews.service;

import com.aqka.interviews.entities.*;
import com.aqka.interviews.exception.ApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;


@Service
@Slf4j
public final class MeetingService {

    @Autowired
    private StoreData storeData;

    private final int DATE_REQUEST_AND_EMPLOYEE_ID_LENGTH = 26;
    private final int MEETING_START_TIME_AND_DURATION_LENGTH = 18;

    /**
     */
    public void addWorkTime( String timeStart, String timeEnd ){
        log.info("addWorkTime-RUN timeStart:{}  timeEnd: {}", timeStart, timeEnd );

         if(Objects.isNull (timeStart ) || timeStart.equals("") || timeStart.length() != 4 ||
            Objects.isNull (timeEnd ) || timeEnd.equals("") || timeEnd.length() != 4 ||
              !MeetingUtil.isStringInt(timeStart) ||  !MeetingUtil.isStringInt(timeEnd)
          ){
            throw new ApiRequestException("Time inserted wrong");
        }
        int tStart = new Integer(timeStart).intValue();
        int tEnd  = new Integer(timeEnd).intValue();
        if(tStart >= tEnd){
            throw new ApiRequestException("Time inserted wrong, Time-Start need to be more than Time-End");
        }
        WorkTime workTime = new WorkTime();
        workTime.setTimeStart( tStart );
        workTime.setTimeEnd( tEnd );

        storeData.setWorkTime(workTime);
        log.info("addEvent-End ");
    }

    /**
     * Method that adds a meeting if the entered date range is free,
     * otherwise it returns an Excetion with a specific message
     *
      * @param meetingRequest
     */
    public void addNewMeeting(MeetingRequest meetingRequest){
        log.info("addNewMeeting-RUN meetingRequest:{}", meetingRequest );
        if(!storeData.workTimeExist()){
            throw new ApiRequestException("Work Time range not exist, insert by: add-work-time");
        }
        // 2011-03-17 10:17:06 EMP001   :DateRequestAndEmployeeId
        // 2011-03-21 09:00 2           :MeetingStartTimeAndDuration
        if(Objects.isNull( meetingRequest.getDateRequestAndEmployeeId()) || meetingRequest.getDateRequestAndEmployeeId().length() <  DATE_REQUEST_AND_EMPLOYEE_ID_LENGTH ||
          Objects.isNull( meetingRequest.getMeetingStartTimeAndDuration() ) || meetingRequest.getMeetingStartTimeAndDuration().length() <  MEETING_START_TIME_AND_DURATION_LENGTH ){
            throw new ApiRequestException("DateRequestAndEmployeeId need to bee long:"
                    +  DATE_REQUEST_AND_EMPLOYEE_ID_LENGTH + " and MeetingStartTimeAndDuration:" + MEETING_START_TIME_AND_DURATION_LENGTH );
        }
        MeetingDTO meetingDTO = buildMeetingDTO(meetingRequest);

        if(storeData.timeAvailable(meetingDTO)){
            storeData.addMeeting( meetingDTO );
        }
        log.info("addNewMeeting-end" );
    }



    public MeetingDTO  buildMeetingDTO(MeetingRequest meetingRequest){
        MeetingDTO meetingDTO = new MeetingDTO();

        //2011-03-17 10:17:06 EMP001
        String dateRequest_hour_EmployeeId[] =  meetingRequest.getDateRequestAndEmployeeId().split(" ");
        String startTime_hour_durationHours[] =  meetingRequest.getMeetingStartTimeAndDuration().split(" ");

        meetingDTO.setEmployeeId(dateRequest_hour_EmployeeId[2]);
        try {
            meetingDTO.setMeetingDate(  MeetingUtil.getDateByString(  dateRequest_hour_EmployeeId[0]+" "+
                                                                        dateRequest_hour_EmployeeId[1]
                                                                      ,MeetingUtil.yyyyMMdd_HHmmSS ) );

            meetingDTO.setTimeStart( MeetingUtil.getDateByString(  startTime_hour_durationHours[0]+" "+
                                                                    startTime_hour_durationHours[1],
                                                                    MeetingUtil.yyyyMMdd_HHmm )
                                                                    );
            meetingDTO.setTimeEnd(  MeetingUtil.addHours(meetingDTO.getTimeStart(), new Integer(startTime_hour_durationHours[2]).intValue() ) );

        } catch (ParseException e) {
            log.error("Exception in buildMeetingDTO " , e );
            throw new ApiRequestException("Format date wrong!  message:"+ e.getMessage() );
        }
        return meetingDTO;
    }

    public Collection<Meeting> getMeetingsByDate(String dateMeetingStr) {
        log.info("getMeetingsByDate-RUN dateMeeting:{}", dateMeetingStr );

        if(Objects.isNull(dateMeetingStr)) {
            throw new ApiRequestException("Date for search is not present" );
        }
        Date dateMeeting = null;
        try {
            dateMeeting = MeetingUtil.getDateStringToDate(dateMeetingStr, MeetingUtil.ddMMyyyy );
            return storeData.getMeetingsByData(dateMeeting);

        } catch (ParseException e) {
            throw new ApiRequestException("Date format wrong!" );
        }


    }

    public Collection<MeetingResponse> getAllMeetings() {
        log.info("getAllMeetings-RUN " );
        return storeData.getAllMeetings();
    }

}
