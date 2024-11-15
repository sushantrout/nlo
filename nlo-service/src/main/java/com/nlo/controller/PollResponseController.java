package com.nlo.controller;

import com.nlo.model.ApiResponse;
import com.nlo.model.PollResponseDTO;
import com.nlo.model.PollResult;
import com.nlo.model.RatingDTO;
import com.nlo.service.PollResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/poll/response")
@RequiredArgsConstructor
public class PollResponseController {
    private final PollResponseService service;

    @PostMapping
    public PollResponseDTO answer(@RequestBody PollResponseDTO pollResponseDTO) {
        return service.answer(pollResponseDTO);
    }

    @GetMapping("get-poll-result/{pollId}")
    public PollResult getLatestPollResult(@PathVariable String pollId) {
        return service.getLatestPollResult(pollId);
    }

    @GetMapping(value = "/top-rated")
    public ApiResponse getTopRatedList(Pageable pageable) {
        Page<RatingDTO> resp = service.getTopRatedList(pageable);
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
