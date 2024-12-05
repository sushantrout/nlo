package com.nlo.mapper;

import com.nlo.entity.Constituency;
import com.nlo.entity.Meeting;
import com.nlo.model.ConstituencyDTO;
import com.nlo.model.MeetingDTO;
import org.springframework.stereotype.Component;

@Component
public class MeetingMapper implements BaseMapper<MeetingDTO, Meeting> {

    @Override
    public MeetingDTO toDto(Meeting meeting) {
        if (meeting == null) {
            return null;
        }
        MeetingDTO dto = new MeetingDTO();
        dto.setId(meeting.getId());
        dto.setTitle(meeting.getTitle());
        dto.setMeetingTime(meeting.getMeetingTime());
        dto.setDescription(meeting.getDescription());
        dto.setCommunicationType(meeting.getCommunicationType());
        dto.setCreatedBy(meeting.getCreatedBy());
        dto.setUpdatedBy(meeting.getUpdatedBy());
        dto.setCreatedOn(meeting.getCreatedOn());
        dto.setUpdatedOn(meeting.getUpdatedOn());
        dto.setActive(meeting.getActive());
        dto.setDeleted(meeting.getDeleted());
        dto.setMeetingId(meeting.getMeetingId());
        dto.setMeetingToken(meeting.getMeetingToken());
        if (meeting.getConstituency() != null) {
            ConstituencyDTO constituencyDTO = new ConstituencyDTO();
            constituencyDTO.setId(meeting.getConstituency().getId());
            constituencyDTO.setTitle(meeting.getConstituency().getTitle());
            dto.setConstituencyDTO(constituencyDTO);
        }

        return dto;
    }

    @Override
    public Meeting toEntity(MeetingDTO meetingDTO) {
        if (meetingDTO == null) {
            return null;
        }
        Meeting meeting = new Meeting();
        meeting.setId(meetingDTO.getId());
        meeting.setTitle(meetingDTO.getTitle());
        meeting.setMeetingTime(meetingDTO.getMeetingTime());
        meeting.setDescription(meetingDTO.getDescription());
        meeting.setCommunicationType(meetingDTO.getCommunicationType());
        meeting.setCreatedBy(meetingDTO.getCreatedBy());
        meeting.setUpdatedBy(meetingDTO.getUpdatedBy());
        meeting.setCreatedOn(meetingDTO.getCreatedOn());
        meeting.setUpdatedOn(meetingDTO.getUpdatedOn());
        meeting.setActive(meetingDTO.getActive());
        meeting.setDeleted(meetingDTO.getDeleted());
        meeting.setMeetingId(meetingDTO.getMeetingId());
        meeting.setMeetingToken(meetingDTO.getMeetingToken());

        if (meetingDTO.getConstituencyDTO() != null) {
            Constituency constituency = new Constituency();
            constituency.setId(meetingDTO.getConstituencyDTO().getId());
            constituency.setTitle(meetingDTO.getConstituencyDTO().getTitle());
            meeting.setConstituency(constituency);
        }

        return meeting;
    }
}

