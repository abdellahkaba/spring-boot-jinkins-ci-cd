package com.isi.service;

import com.isi.dto.CategoryRequest;
import com.isi.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse newCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategory();
    CategoryResponse updateCategory(CategoryRequest request);
    void deleteCategoryById(Long id);
}
