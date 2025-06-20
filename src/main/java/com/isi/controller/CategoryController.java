package com.isi.controller;


import com.isi.dto.CategoryRequest;
import com.isi.dto.CategoryResponse;
import com.isi.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Categories")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponse> newCategory(
            @Valid @RequestBody CategoryRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newCategory(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCategory());
    }

    @PutMapping
    public ResponseEntity<CategoryResponse> updateCategory(
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse updateCategory = service.updateCategory(request);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        service.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
