package com.isi.mapper;


import com.isi.dto.CategoryRequest;
import com.isi.dto.CategoryResponse;
import com.isi.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Category toCategory (CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
