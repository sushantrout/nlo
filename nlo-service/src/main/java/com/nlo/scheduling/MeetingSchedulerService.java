package com.nlo.scheduling;

import com.nlo.entity.Meeting;
import com.nlo.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingSchedulerService {

    private final MeetingRepository meetingRepository;

    @Scheduled(cron = "0 0/2 * * * *")
    public void checkUpcomingMeetings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMinutesLater = now.plusHours(1);

        List<Meeting> upcomingMeetings = meetingRepository.findMeetingsBetween(now, tenMinutesLater);

        if (!upcomingMeetings.isEmpty()) {
            notifyUsers(upcomingMeetings);
        }
    }

    private void notifyUsers(List<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            log.info("Reminder: Meeting titled '" + meeting.getTitle() + "' is scheduled at " + meeting.getMeetingTime());
        }
    }
}
