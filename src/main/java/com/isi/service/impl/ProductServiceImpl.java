package com.isi.service.impl;

import com.isi.dto.ProductRequest;
import com.isi.dto.ProductResponse;
import com.isi.exception.EntityExistsException;
import com.isi.exception.EntityNotFoundException;
import com.isi.mapper.ProductMapper;
import com.isi.model.Product;
import com.isi.repository.CategoryRepository;
import com.isi.repository.ProductRepository;
import com.isi.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
@AllArgsConstructor
@Getter
@Setter
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;


    @Override
    public ProductResponse newProduct(ProductRequest request) {
        var category = categoryRepository.findById(request.getCategorieId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("categorie.notfound", new Object[]{request.getCategorieId()}, Locale.getDefault())));
        if (repository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("product.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }
        Product product = mapper.toProduct(request);
        product.setCategorie(category);
        var savedProduct = repository.save(product);
        return mapper.toProductResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("product.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> products = repository.findAll();
        return mapper.toProductResponseList(products);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request) {

        var category = categoryRepository.findById(request.getCategorieId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("categorie.notfound", new Object[]{request.getCategorieId()}, Locale.getDefault())));

        if (repository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("product.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }

        var product = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("product.notfound", new Object[]{request.getId()}, Locale.getDefault())));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategorie(category);
        var updateProduct = repository.save(product);
        return mapper.toProductResponse(updateProduct);
    }

    @Override
    public void deleteProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("product.notfound", new Object[]{id}, Locale.getDefault())));
        repository.delete(product);

    }
}
