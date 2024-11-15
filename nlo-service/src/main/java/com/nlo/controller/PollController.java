package com.nlo.controller;

import com.nlo.model.ApiResponse;
import com.nlo.model.PollDTO;
import com.nlo.service.PollService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pool")
public class PollController extends BaseController<PollDTO, PollService> {
    public PollController(PollService service) {
        super(service);
    }

    @GetMapping("get-latest")
    public PollDTO getLatest() {
        return service.getLatest();
    }

    @GetMapping("/mobile")
    public ApiResponse getPageDataForMobile(Pageable pageable) {
        Page<PollDTO> resp = service.getPageDataForMobile(pageable);
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
