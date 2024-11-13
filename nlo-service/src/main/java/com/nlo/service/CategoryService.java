package com.nlo.service;

import com.nlo.entity.Category;
import com.nlo.mapper.CategoryMapper;
import com.nlo.model.CategoryDTO;
import com.nlo.repository.CategoryRepository;
import com.nlo.validation.CategoryValidation;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseServiceImpl<Category, CategoryDTO, CategoryMapper, CategoryValidation, CategoryRepository> {
    protected CategoryService(CategoryRepository repository, CategoryMapper mapper, CategoryValidation validation) {
        super(repository, mapper, validation);
    }
}
