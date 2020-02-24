package com.ico.ltd.spring5mvcrest.api.v1.mapper;

import com.ico.ltd.spring5mvcrest.api.v1.model.CategoryDTO;
import com.ico.ltd.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    public static final String NAME = "Joe";

    public static final long ID = 1L;

    private static final CategoryMapper CATEGORY_MAPPER = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        //given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //when
        CategoryDTO categoryDTO = CATEGORY_MAPPER.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}