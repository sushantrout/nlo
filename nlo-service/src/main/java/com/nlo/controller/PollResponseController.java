package com.nlo.controller;

import com.nlo.model.PollDTO;
import com.nlo.model.PollResponseDTO;
import com.nlo.service.PollResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/poll/response")
@RequiredArgsConstructor
public class PollResponseController {
    private final PollResponseService service;

    @PostMapping
    public PollResponseDTO answer(@RequestBody PollResponseDTO pollResponseDTO) {
        return service.answer(pollResponseDTO);
    }

    @GetMapping("get-latest-poll-result")
    public List<PollResponseDTO> getLatestPollResult() {
        return service.getLatestPollResult();
    }
}
