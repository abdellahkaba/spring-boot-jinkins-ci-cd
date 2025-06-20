package com.isi.controller;


import com.isi.dto.ProductRequest;
import com.isi.dto.ProductResponse;
import com.isi.service.ProductService;
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
@RequestMapping("products")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Products")
public class ProductController {

    private final ProductService service;
    @PostMapping
    public ResponseEntity<ProductResponse> newProduct(
            @Valid @RequestBody ProductRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllProduct());
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(
            @Valid @RequestBody ProductRequest request) {
        ProductResponse updateProduct = service.updateProduct(request);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        service.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
