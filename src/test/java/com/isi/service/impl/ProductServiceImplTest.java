package com.isi.service.impl;

import com.isi.dto.ProductRequest;
import com.isi.dto.ProductResponse;
import com.isi.exception.EntityNotFoundException;
import com.isi.mapper.ProductMapper;
import com.isi.model.Category;
import com.isi.model.Product;
import com.isi.repository.CategoryRepository;
import com.isi.repository.ProductRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock 
    private CategoryRepository categoryRepository;
    @Mock
    private MessageSource messageSource;


    @Test
    void addProductOK() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(getCategory()));
        when(productMapper.toProduct(any())).thenReturn(this.getProduct());
        when(productRepository.save(any())).thenReturn(this.getProduct());
        when(productMapper.toProductResponse(any())).thenReturn(this.getProductResponse());

        ProductResponse response = productService.newProduct(getProductRequest());
        assertNotNull(response);
        assertEquals("Product", response.getName());
    }

    @Test
    void addProductKO_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("categorie.notfound"), any(), any(Locale.class)))
                .thenReturn("The Category with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productService.newProduct(this.getProductRequest()));
        assertEquals("The Category with ID 1 not found", exception.getMessage());
    }

    @Test
    void getProductOK() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        when(productMapper.toProductResponse(any())).thenReturn(getProductResponse());

        ProductResponse response = productService.getProductById(1L);
        assertNotNull(response);
        assertEquals("Product", response.getName());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("product.notfound"), any(), any(Locale.class)))
                .thenReturn("The Product with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productService.getProductById(1L) );
        assertEquals("The Product with ID 1 not found", exception.getMessage());
    }

    @Test
    void deleteProductOK() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        productService.deleteProductById(1L);

        verify(productRepository, times(1)).delete(any());
    }

    @Test
    void deleteProductKO() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("product.notfound"), any(), any(Locale.class)))
                .thenReturn("Product not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.deleteProductById(1L));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void getAllProduct() {
        when(productRepository.findAll()).thenReturn(List.of(this.getProduct()));
        when(productMapper.toProductResponseList(any())).thenReturn(List.of(this.getProductResponse()));

        List<ProductResponse> responses = productService.getAllProduct();
        assertEquals(1, responses.size());
    }

    @Test
    void updateProductOK() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(getCategory()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(getProduct());
        when(productMapper.toProductResponse(any())).thenReturn(getProductResponse());

        ProductResponse response = productService.updateProduct(getProductRequest());
        assertEquals("Product", response.getName());
    }

    @Test
    void updateProductKO_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("categorie.notfound"), any(), any(Locale.class)))
                .thenReturn("The Category with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productService.updateProduct(getProductRequest()));
        assertEquals("The Category with ID 1 not found", exception.getMessage());
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Product");
        product.setDescription("Description product");
        Category category = new Category();
        product.setCategorie(category);
        return product;
    }
    private ProductRequest getProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setId(1L);
        request.setName("Product");
        request.setDescription("Description product");
        request.setCategorieId(1L);
        return request;
    }
    private ProductResponse getProductResponse() {
        ProductResponse response = new ProductResponse();
        response.setName("Product");
        response.setDescription("Description product");
        response.setPrice(200);
        response.setQuantity(3);
        response.setCategorieId(1L);
        return response;
    }
    private Category getCategory() {
        Category category = new Category();
        category.setId(3L);
        return category;
    }

}