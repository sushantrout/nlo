package com.nlo.service;

import com.nlo.entity.Meeting;
import com.nlo.mapper.MeetingMapper;
import com.nlo.model.MeetingDTO;
import com.nlo.repository.MeetingRepository;
import com.nlo.validation.MeetingValidation;
import org.springframework.stereotype.Service;

@Service
public class MeetingService extends BaseServiceImpl<Meeting, MeetingDTO, MeetingMapper, MeetingValidation, MeetingRepository> {
    protected MeetingService(MeetingRepository repository, MeetingMapper mapper, MeetingValidation validation) {
        super(repository, mapper, validation);
    }
}
