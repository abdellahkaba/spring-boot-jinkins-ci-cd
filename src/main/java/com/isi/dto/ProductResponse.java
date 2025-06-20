package com.isi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class ProductResponse {

    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private Long categorieId;
    private String categorieName;
}
