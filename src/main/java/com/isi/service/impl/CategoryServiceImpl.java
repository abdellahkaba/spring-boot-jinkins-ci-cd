package com.isi.service.impl;


import com.isi.dto.CategoryRequest;
import com.isi.dto.CategoryResponse;
import com.isi.exception.EntityExistsException;
import com.isi.exception.EntityNotFoundException;
import com.isi.mapper.CategoryMapper;
import com.isi.model.Category;
import com.isi.repository.CategoryRepository;
import com.isi.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
@Setter
@Getter
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final MessageSource messageSource;


    @Override
    public CategoryResponse newCategory(CategoryRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("categorie.exists", new Object[]{request.getName()}, Locale.getDefault()));
        }
        Category category = mapper.toCategory(request);
        var saveCategory = repository.save(category);
        return mapper.toCategoryResponse(saveCategory);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return repository.findById(id)
                .map(mapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("categorie.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return mapper.toCategoryResponseList(repository.findAll());
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request) {
        var category = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("categorie.notfound", new Object[]{request.getId()}, Locale.getDefault())));

        if (repository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("categorie.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        var updateCategory = repository.save(category);
        return mapper.toCategoryResponse(updateCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("categorie.notfound", new Object[]{id}, Locale.getDefault())));
        repository.delete(category);

    }
}
