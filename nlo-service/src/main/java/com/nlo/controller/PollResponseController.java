package com.nlo.controller;

import com.nlo.model.PollResponseDTO;
import com.nlo.model.PollResult;
import com.nlo.service.PollResponseService;
import lombok.RequiredArgsConstructor;
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
}
