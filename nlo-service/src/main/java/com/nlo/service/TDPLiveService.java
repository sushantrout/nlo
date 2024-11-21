package com.nlo.service;

import com.nlo.entity.TDPLive;
import com.nlo.mapper.TDPLiveMapper;
import com.nlo.model.TDPLiveDTO;
import com.nlo.repository.TDPLiveRepository;
import com.nlo.validation.TDPLiveValidation;
import org.springframework.stereotype.Service;

@Service
public class TDPLiveService extends BaseServiceImpl<TDPLive, TDPLiveDTO, TDPLiveMapper, TDPLiveValidation, TDPLiveRepository> {
    protected TDPLiveService(TDPLiveRepository repository, TDPLiveMapper mapper, TDPLiveValidation validation) {
        super(repository, mapper, validation);
    }
}
