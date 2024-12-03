package com.nlo.repository;

import com.nlo.entity.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends BaseRepository<Meeting> {
    @Query("SELECT m FROM Meeting m WHERE m.meetingTime BETWEEN :startTime AND :endTime")
    List<Meeting> findMeetingsBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
