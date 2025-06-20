package com.isi.mapper;


import com.isi.dto.ProductRequest;
import com.isi.dto.ProductResponse;
import com.isi.model.Category;
import com.isi.model.Product;
import com.isi.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses =
        {
                CategoryRepository.class,
        })
public interface ProductMapper {


    @Mapping(source = "categorieId", target = "categorie", qualifiedByName = "mapCategorieIdToCategory")
    Product toProduct(ProductRequest request);
    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "categorie.name", target = "categorieName")
    ProductResponse toProductResponse(Product product);
    List<ProductResponse> toProductResponseList(List<Product> products);


    @Named("mapCategorieIdToCategory")
    static Category mapCategorieIdToCategory(Long categorieId) {
        if (categorieId == null) {
            return null;
        }
        Category categorie = new Category();
        categorie.setId(categorieId);
        return categorie;
    }

}
