package com.aqka.interviews.service;

import com.aqka.interviews.entities.Meeting;
import com.aqka.interviews.entities.MeetingDTO;
import com.aqka.interviews.entities.MeetingResponse;
import com.aqka.interviews.entities.WorkTime;
import com.aqka.interviews.exception.ApiRequestException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoreData {

    private WorkTime workTime;
    private HashMap<Date, List<MeetingDTO>> meetings = new HashMap<>();

    /**
     * Check if the time is available for the meeting
     * @param meetingNew
     * @return
     */
    public boolean timeAvailable(MeetingDTO meetingNew){
        Date dateMeeting = MeetingUtil.truncateTime( meetingNew.getTimeStart() );

        if(! MeetingUtil.checkIfInWorkingHours(  workTime , meetingNew )  ){
            throw new ApiRequestException("Time inserted off on range work, Insert between Hour-Start: "+
                      String.valueOf( workTime.getTimeStart() ) + " - " +
                      String.valueOf( workTime.getTimeEnd() ) );
        }
        if(!meetings.containsKey(dateMeeting)){
            return true;
        }
        List<MeetingDTO> listMeetings = meetings.get(dateMeeting);
        for(MeetingDTO meetingExist : listMeetings ){
           boolean timeFree = MeetingUtil.checkTimeFree( meetingNew, meetingExist );
           if(!timeFree){
               String message = "Time not available, exist booking " +
                                 MeetingUtil.getHourMinutes( meetingExist.getTimeStart() ) + " - " +
                                 MeetingUtil.getHourMinutes( meetingExist.getTimeEnd() );
               throw new ApiRequestException( message );
           }
        }
        return true;
    }


    public void addMeeting( MeetingDTO meetingDTO) {
        Date dateMeeting = MeetingUtil.truncateTime( meetingDTO.getTimeStart() );

        if( meetings.containsKey( dateMeeting ) ){
            meetings.get(dateMeeting).add(meetingDTO);
        }else{
            List<MeetingDTO> listMeetingsForDay =new ArrayList<>();
            listMeetingsForDay.add(meetingDTO);
            meetings.put(dateMeeting , listMeetingsForDay );
        }
    }

    public boolean workTimeExist() {
        if(Objects.nonNull(workTime)){
            return true;
        }else{
            return false;
        }
    }

    public List<Meeting> getMeetingsByData(Date dateMeeting) {
        if(!meetings.containsKey(dateMeeting)){
            throw new ApiRequestException("No bookings for this date: "+ dateMeeting );
        }
        List<MeetingDTO> list = meetings.get(dateMeeting);
        return getMeetingsByDaySortedByTimeStart(list);
    }

    public List<MeetingResponse> getAllMeetings() {
        List<MeetingResponse> result = new ArrayList<>();

        if(meetings.size() == 0){
            throw new ApiRequestException("There are no meetings!" );
        }
        // short meetings by date
        HashMap<Date, List<MeetingDTO>> mapSorted
                = meetings.entrySet()
                .stream()
                .sorted((i1, i2)
                        -> i1.getKey().compareTo(
                        i2.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        // build response
        mapSorted.forEach((key, value) -> {
            MeetingResponse meetingResponse = new MeetingResponse();
            meetingResponse.setDateMeeting( MeetingUtil.getDateFormatString( key , MeetingUtil.ddMMyyyy ) );
            meetingResponse.setListMeetings( getMeetingsByDaySortedByTimeStart( value) );
            result.add( meetingResponse );
        });

        return result;
    }

    /**
     * sort  list List<MeetingDTO> list
     *
     * @param list
     * @return
     */
    private List<Meeting> getMeetingsByDaySortedByTimeStart(List<MeetingDTO> list) {
        List<MeetingDTO> listOrdered = list.stream().sorted(Comparator.comparing(MeetingDTO::getTimeStart)).collect(Collectors.toList());
        List<Meeting> result = listOrdered.stream()
                .map(p -> new Meeting( MeetingUtil.getHourMinutes( p.getTimeStart() ),MeetingUtil.getHourMinutes( p.getTimeEnd()), p.getEmployeeId()))
                .collect(Collectors.toList());
        return result;
    }


    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }
}
