package com.nlo.service;

import com.nlo.entity.Constituency;
import com.nlo.mapper.ConstituencyMapper;
import com.nlo.model.ConstituencyDTO;
import com.nlo.repository.ConstituencyRepository;
import com.nlo.validation.ConstituencyValidation;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ConstituencyService extends BaseServiceImpl<Constituency, ConstituencyDTO, ConstituencyMapper, ConstituencyValidation, ConstituencyRepository> {
    public ConstituencyService(ConstituencyRepository repository, ConstituencyMapper mapper, ConstituencyValidation validation) {
        super(repository, mapper, validation);
    }

    public List<ConstituencyDTO> findDropdownData() {
        return repository.findByDeletedFalseOrDeletedIsNull().stream().map(mapper::toDto)
                .filter(d -> Objects.nonNull(d.getTitle()))
                .sorted(Comparator.comparing(ConstituencyDTO::getTitle))
                .toList();
    }
}
