package com.isi.service.impl;

import com.isi.dto.CategoryRequest;
import com.isi.dto.CategoryResponse;
import com.isi.exception.EntityExistsException;
import com.isi.exception.EntityNotFoundException;
import com.isi.mapper.CategoryMapper;
import com.isi.model.Category;
import com.isi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private CategoryServiceImpl service;
    @Mock
    private CategoryMapper mapper;

    @Test
    void addCategoryOK() {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(mapper.toCategory(any())).thenReturn(getCategory());
        when(repository.save(any())).thenReturn(getCategory());
        when(mapper.toCategoryResponse(any())).thenReturn(getCategoryResponse());

        CategoryResponse response = service.newCategory(getCategoryRequest());

        assertNotNull(response);
        assertEquals("Category", response.getName());
    }

    @Test
    void addCategoryKO() {
        when(repository.findByName(anyString())).thenReturn(Optional.of(getCategory()));
        when(messageSource.getMessage(eq("categorie.exists"), any(), any(Locale.class)))
                .thenReturn("Category already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> service.newCategory(getCategoryRequest()));

        assertEquals("Category already exists", exception.getMessage());
    }

    @Test
    void getCategoryOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getCategory()));
        when(mapper.toCategoryResponse(any())).thenReturn(getCategoryResponse());

        CategoryResponse response = service.getCategoryById(1L);

        assertNotNull(response);
        assertEquals("Category", response.getName());
    }

    @Test
    void getCategoryKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("categorie.notfound"), any(), any(Locale.class)))
                .thenReturn("Category not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.getCategoryById(1L));

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void getAllCategories() {
        when(repository.findAll()).thenReturn(List.of(getCategory()));
        when(mapper.toCategoryResponseList(any())).thenReturn(List.of(getCategoryResponse()));

        List<CategoryResponse> responses = service.getAllCategory();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void updateCategoryOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getCategory()));
        when(repository.save(any())).thenReturn(getCategory());
        when(mapper.toCategoryResponse(any())).thenReturn(getCategoryResponse());

        CategoryResponse response = service.updateCategory(getCategoryRequest());

        assertNotNull(response);
        assertEquals("Category", response.getName());
    }

    @Test
    void updateCategoryKO_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("categorie.notfound"), any(), any(Locale.class)))
                .thenReturn("Category not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.updateCategory(getCategoryRequest()));

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void updateCategoryKO_NameExists() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getCategory()));
        when(repository.findByName(anyString())).thenReturn(Optional.of(getCategory()));
        when(messageSource.getMessage(eq("categorie.exists"), any(), any(Locale.class)))
                .thenReturn("Category already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> service.updateCategory(getCategoryRequest()));
        assertEquals("Category already exists", exception.getMessage());
    }

    @Test
    void deleteCategoryByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getCategory()));

        service.deleteCategoryById(1L);

        verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteCategoryByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("categorie.notfound"), any(), any(Locale.class)))
                .thenReturn("Category not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.deleteCategoryById(1L));

        assertEquals("Category not found", exception.getMessage());
    }

    private CategoryRequest getCategoryRequest() {
        CategoryRequest request = new CategoryRequest();
        request.setId(1L);
        request.setName("Category");
        request.setDescription("Description category");
        return request;
    }

    private Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category");
        category.setDescription("Description category");
        return category;
    }

    private CategoryResponse getCategoryResponse() {
        return new CategoryResponse(1L, "Category", "Description category");
    }


}