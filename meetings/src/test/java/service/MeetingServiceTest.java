package service;

import com.aqka.interviews.entities.*;
import com.aqka.interviews.service.MeetingService;
import com.aqka.interviews.service.StoreData;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class MeetingServiceTest {

    @InjectMocks
    private MeetingService meetingService;

    @Mock
    private StoreData storeData;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addNewMeeting() {

        MeetingRequest meetingRequest = new MeetingRequest(
                "2011-03-17 10:17:06 EMP001",
                "2011-03-21 09:00 2"
        );
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setEmployeeId("EMP001");

        when( storeData.workTimeExist (
        ) ).thenReturn( true );

        meetingService.addNewMeeting ( meetingRequest );

        assertThat( "EMP001",
                is( meetingDTO.getEmployeeId() ));

    }

    @Test
    public void getMeetingsByDate() {

        List<Meeting> listMeetings = new ArrayList<>();
        listMeetings.add( new Meeting( null, null, null) );

        when( storeData.getMeetingsByData ( any(Date.class)
        ) ).thenReturn( listMeetings );

        Collection<Meeting> result =  meetingService.getMeetingsByDate ( "21-03-2011" );
        assertThat( 1 ,
                is( listMeetings.size() ));
    }


    @Test
    public void getAllMeetings() {
        Collection<MeetingResponse> resul =  meetingService.getAllMeetings ();
        assertThat( 0 ,
                is( resul.size() ));
    }




}
