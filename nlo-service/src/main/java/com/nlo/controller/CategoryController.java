package com.nlo.controller;

import com.nlo.model.CategoryDTO;
import com.nlo.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController extends BaseController<CategoryDTO, CategoryService> {
    public CategoryController(CategoryService service) {
        super(service);
    }
}
