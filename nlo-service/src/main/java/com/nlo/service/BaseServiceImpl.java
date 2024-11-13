package com.nlo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlo.entity.BaseEntity;
import com.nlo.mapper.BaseMapper;
import com.nlo.model.BaseDTO;
import com.nlo.repository.BaseRepository;
import com.nlo.service.BaseService;
import com.nlo.validation.Validation;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Log4j2
@Service
@Transactional
public abstract class BaseServiceImpl<E extends BaseEntity, D extends BaseDTO, M extends BaseMapper<D, E>, V extends Validation<D>, R extends BaseRepository<E>> implements BaseService<D> {
    public final R repository;
    public final M mapper;
    public final V validation;
    @Autowired
    public ObjectMapper objectMapper;
    @Lazy
    @Autowired
    public EntityManager entityManager;

    protected BaseServiceImpl(R repository, M mapper, V validation) {
        this.repository = repository;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    public Page<D> getAll(Pageable pageable) {
        Page<E> dataPage = repository.findByDeletedFalseOrDeletedIsNull(pageable);

        return dataPage.map(mapper::toDto);
    }


    @Override
    public Optional<D> getById(String id) {
        Optional<E> dataOpt = repository.findById(id);
        return dataOpt.map(mapper::toDto);
    }

    @Override
    public D create(D d) throws ValidationException {
        validation.validate(d);
        E entity = mapper.toEntity(d);
        E savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<D> createAll(List<D> list) throws ValidationException {
        List<D> dtoList = new ArrayList<>();
        for (D d : list) {
            dtoList.add(create(d));
        }
        return dtoList;
    }

    @Override
    public D update(String id, D d) throws ValidationException {
        validation.validate(d);
        Optional<E> existingVal = repository.findById(id);
        if (existingVal.isPresent()) {
            E entity = mapper.toEntity(d);
            E savedEntity = repository.save(entity);
            return mapper.toDto(savedEntity);
        } else {
            //throw new ValidationException("Record not found.", ErrorCode.EMPTY_OR_NULL_VALUE_FOUND, "GenericService.update");
        }
        return null;
    }

    @Override
    public Optional<D> partialUpdate(String id, Map<String, Object> properties) {
        return repository.findById(id)
                .map(entity -> {
                    try {
                        Set<String> propKeys = properties.keySet();
                        for (String key : propKeys) {
                            Class<?> aClass = entity.getClass();
                            if(Objects.equals("isActive", key)) {
                                aClass = aClass.getSuperclass().getSuperclass();
                            }
                            Field field = aClass.getDeclaredField(key);
                            field.setAccessible(true);
                            Object value = properties.get(key);

                            if (Objects.nonNull(value)) {
                                if (value instanceof Map<?, ?> currentObj) {
                                    Class<?> fieldType = field.getType();
                                    if (Arrays.stream(fieldType.getAnnotations()).anyMatch(e -> e.annotationType().equals(Entity.class))) {
                                        value = Optional.ofNullable(currentObj.get("id"))
                                                .map(refferenceId -> entityManager.find(fieldType, refferenceId))
                                                .orElse(null);
                                    } else {
                                        value = objectMapper.convertValue(value, fieldType);
                                    }
                                } else if (isValidOffsetDateTime(value.toString())) {
                                    value = OffsetDateTime.parse(value.toString());
                                }
                            }

                            field.set(entity, value);
                        }
                        E save = repository.save(entity);
                        return mapper.toDto(save);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                });
    }

    // Method to check if the value can be parsed to OffsetDateTime
    private boolean isValidOffsetDateTime(String value) {
        if (value == null) {
            return false;
        }
        try {
            OffsetDateTime.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    @Override
    public void delete(String id) throws ValidationException {
        Optional<E> optData = repository.findById(id);
        if (optData.isPresent()) {
            optData.get().setActive(false);
            optData.get().setDeleted(true);
            repository.save(optData.get());
        } else {
            //throw new ValidationException("Record not found.", "ErrorCode.EMPTY_OR_NULL_VALUE_FOUND", );
        }
    }

    @Override
    public void deleteAll(List<String> ids) {
        ids.forEach(id -> {
                    try {
                        delete(id);
                    } catch (ValidationException e) {
                        log.error("Error while deleting entity with id: {}", id);
                    }
                }
        );
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Autowired
    public List<D> all() {
        return mapper.toDtoList(repository.findAll());
    }

}