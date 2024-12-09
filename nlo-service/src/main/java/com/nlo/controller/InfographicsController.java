package com.nlo.controller;

import com.nlo.model.InfographicsDTO;
import com.nlo.model.ReactionDTO;
import com.nlo.service.InfographicsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/infographics")
public class InfographicsController extends BaseController<InfographicsDTO, InfographicsService> {
    public InfographicsController(InfographicsService service) {
        super(service);
    }

    @PostMapping("{infographicsId}/reaction")
    public InfographicsDTO reaction(@RequestBody ReactionDTO reactionDTO, @PathVariable String  infographicsId){
        return service.reaction(infographicsId, reactionDTO);
    }

    @PostMapping("{infographicsId}/view")
    public void view(@PathVariable String  infographicsId){
        service.view(infographicsId);
    }
}
