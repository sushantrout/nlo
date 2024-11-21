package com.nlo.service;

import com.nlo.entity.QuickTV;
import com.nlo.mapper.QuickTVMapper;
import com.nlo.model.QuickTVDTO;
import com.nlo.repository.QuickTVRepository;
import com.nlo.validation.QuickTVValidation;
import org.springframework.stereotype.Service;

@Service
public class QuickTVService extends BaseServiceImpl<QuickTV, QuickTVDTO, QuickTVMapper, QuickTVValidation, QuickTVRepository> {
    protected QuickTVService(QuickTVRepository repository, QuickTVMapper mapper, QuickTVValidation validation) {
        super(repository, mapper, validation);
    }
}
