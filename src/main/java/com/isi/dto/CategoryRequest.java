package com.isi.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CategoryRequest {

    private Long id;
    @NotBlank(message = "Category Name is require")
    private String name;
    @NotBlank(message = "Category Description is require")
    private String description;
}
