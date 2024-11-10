package com.nlo.controller;

import com.nlo.model.ApiResponse;
import com.nlo.service.BaseService;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public abstract class BaseController<D, S extends BaseService<D>> {
    public final S service;

    @GetMapping
    public ApiResponse getAll(Pageable pageable) {
        if(pageable.getPageSize() == -1) {
            return ApiResponse.builder()
                    .data(service.all())
                    .build();
        } else {
            Page<D> resp = service.getAll(pageable);
            return ApiResponse.builder()
                    .data(resp.get())
                    .status(HttpStatus.OK.toString())
                    .totalPages(resp.getTotalPages())
                    .total(resp.getTotalElements())
                    .data(resp.getContent())
                    .pageNum(resp.getNumber())
                    .pageSize(pageable.getPageSize())
                    .currentPageSize(resp.getNumberOfElements())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable String id) {
        Optional<D> optionalEntity = service.getById(id);
        return optionalEntity.map(entity -> ApiResponse.builder()
                        .status(HttpStatus.OK.toString())
                        .data(entity).build())
                .orElseGet(() -> ApiResponse.builder()
                        .status(HttpStatus.NO_CONTENT.toString()).build());
    }

    @PostMapping
    public ApiResponse create(@RequestBody D dto) throws ValidationException {
        D savedDto = service.create(dto);
        return ApiResponse.builder()
                .status(HttpStatus.CREATED.toString())
                .data(savedDto).build();
    }

    @PostMapping("insert-all")
    public ApiResponse createAll(@RequestBody List<D> dtoList) throws ValidationException {
        List<D> saveddtoList = service.createAll(dtoList);
        return ApiResponse.builder()
                .status(HttpStatus.CREATED.toString())
                .data(saveddtoList)
                .total((long) saveddtoList.size()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable String id, @RequestBody D dto) throws ValidationException {
        D updateDto = service.update(id, dto);
        return ApiResponse.builder()
                .status(HttpStatus.OK.toString())
                .data(updateDto).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse partialUpdate(@PathVariable String id, @RequestBody Map<String, Object> properties) throws ValidationException {
        Optional<D> optionalEntity = service.partialUpdate(id, properties);
        return optionalEntity.map(entity -> ApiResponse.builder()
                        .status(HttpStatus.OK.toString())
                        .data(entity).build())
                .orElseGet(() -> ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.toString()).build());
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable String id) throws ValidationException {
        service.delete(id);
        return ApiResponse.builder()
                .status(HttpStatus.OK.toString()).build();
    }

    @GetMapping("/search")
    public ApiResponse gsWithPage(@RequestParam String searchKey, Pageable pageable) {
        return null;
    }
}