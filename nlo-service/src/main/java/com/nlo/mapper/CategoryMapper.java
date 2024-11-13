package com.nlo.mapper;

import com.nlo.entity.Category;
import com.nlo.model.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements BaseMapper<CategoryDTO, Category> {

    @Override
    public CategoryDTO toDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        return categoryDTO;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setTitle(categoryDTO.getTitle());
        return category;
    }
}
