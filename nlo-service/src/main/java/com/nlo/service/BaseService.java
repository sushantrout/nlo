package com.nlo.service;

import jakarta.xml.bind.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<D> {
    Page<D> getAll(Pageable pageable);
    Optional<D> getById(String id);
    D create(D entity) throws ValidationException;
    List<D> createAll(List<D> list) throws ValidationException;
    D update(String id, D entity) throws ValidationException;
    Optional<D> partialUpdate(String id, Map<String, Object> properties) throws ValidationException;
    void delete(String id) throws ValidationException;
    void deleteAll(List<String> id);
    boolean existsById(String id);
    List<D> all();
    default Optional<D> getById(String id, String shareId){return null;}
}