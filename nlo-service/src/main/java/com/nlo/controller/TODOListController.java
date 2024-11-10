package com.nlo.controller;

import com.nlo.model.ReactionDTO;
import com.nlo.model.TODOListDTO;
import com.nlo.service.TODOListService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/todo-list")
public class TODOListController extends BaseController<TODOListDTO, TODOListService> {
    public TODOListController(TODOListService service) {
        super(service);
    }

    @PostMapping("{todoId}/reaction")
    public TODOListDTO reaction(@RequestBody ReactionDTO reactionDTO, @PathVariable String  todoId){
        return service.reaction(todoId, reactionDTO);
    }
}
