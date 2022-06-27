package com.aqka.interviews.service;


import com.aqka.interviews.entities.MeetingDTO;
import com.aqka.interviews.entities.WorkTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class MeetingUtil {


    public final static DateFormat yyyyMMdd_HHmmSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat yyyyMMdd_HHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");

     public static String getDateFormatString( Date date ,DateFormat dateFormat ){
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         return dateFormat.format(cal.getTime());
     }
    public static Date getDateStringToDate( String dateStr ,DateFormat dateFormat ) throws ParseException {
        return dateFormat.parse(dateStr);
    }

     public static Date setHourAndMinutes(Date date, int hour, int minutes){
         Date dateNew = truncateTime( date );

         Calendar calendar = Calendar.getInstance();
         calendar.setTime( dateNew );
         calendar.set(Calendar.HOUR, hour );
         calendar.set(Calendar.MINUTE, minutes );
         return calendar.getTime();
     }

     public static Date truncateTime(Date date ){
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         cal.set(Calendar.HOUR_OF_DAY, 0);
         cal.set(Calendar.MINUTE, 0);
         cal.set(Calendar.SECOND, 0);
         cal.set(Calendar.MILLISECOND, 0);
         return new Date( cal.getTimeInMillis());
     }

      public static Date getDateByString( String dateStr , DateFormat dateFormat ) throws ParseException {
          Date date = dateFormat.parse(dateStr);
          return date;
      }

      public static Date addHours(Date date, int hour){
          Calendar cal = new GregorianCalendar();
          cal.setTime(date);
          cal.add(Calendar.HOUR_OF_DAY, hour);
          return cal.getTime();
      }

    public static boolean checkTimeFree(MeetingDTO meetingDtoNew, MeetingDTO meetingDtoExist) {

        int timeStartNew = MeetingUtil.getHourAndMinutes( meetingDtoNew.getTimeStart() );
        int timeEndNew = MeetingUtil.getHourAndMinutes( meetingDtoNew.getTimeEnd() );

        int timeStartExist = MeetingUtil.getHourAndMinutes( meetingDtoExist.getTimeStart() );
        int timeEndExist = MeetingUtil.getHourAndMinutes( meetingDtoExist.getTimeEnd() );

        if ((timeStartNew >=  timeStartExist && timeStartNew >=  timeEndExist &&
             timeEndNew >= timeStartExist && timeEndNew >= timeEndExist)
            ||
           (timeStartNew <=  timeStartExist && timeStartNew <=  timeEndExist &&
            timeEndNew <= timeStartExist && timeEndNew <= timeEndExist)
        ) {
            return true;
        }
        return false;
    }

    public static boolean checkIfInWorkingHours(WorkTime workTime, MeetingDTO meetingDTO) {
        int timeStart = MeetingUtil.getHourAndMinutes( meetingDTO.getTimeStart() );
        int timeEnd = MeetingUtil.getHourAndMinutes( meetingDTO.getTimeEnd() );

          if (timeStart >=  workTime.getTimeStart() && timeStart <=  workTime.getTimeEnd() &&
              timeEnd >= workTime.getTimeStart() && timeEnd <= workTime.getTimeEnd()) {
              return true;
          }
         return false;
    }

    private static int getHourAndMinutes( Date date) {
        String result = String.valueOf(date.getHours());
        if(date.getMinutes() > 0) {
            result+=date.getMinutes();
        }else{
            result+="00";
        }
        return new Integer(result).intValue();
    }
    public static String getHourMinutes( Date date ) {
        String result = String.valueOf(date.getHours());
        if(date.getMinutes() > 0) {
            result+=":"+date.getMinutes();
        }else{
            result+=":00";
        }
        return result;
    }
    
    public static boolean isStringInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }

}
