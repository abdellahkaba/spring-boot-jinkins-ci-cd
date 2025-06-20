package com.isi.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ProductRequest {

    private Long id;
    @NotBlank(message = "Product name is require")
    private String name;
    @NotNull(message = "Product price is require")
    private double price;
    @NotNull(message = "Product quantity is require ")
    private int quantity;
    @NotBlank(message = "Product description is require")
    private String description;

    @NotNull(message = "Category id is require")
    private Long categorieId;


}
