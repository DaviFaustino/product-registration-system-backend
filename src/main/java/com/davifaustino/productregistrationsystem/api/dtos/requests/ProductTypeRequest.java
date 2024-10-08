package com.davifaustino.productregistrationsystem.api.dtos.requests;

import com.davifaustino.productregistrationsystem.business.entities.EnumCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeRequest {

    @NotNull
    @Size(min = 3, max = 32)
    String name;

    EnumCategory category;

    @NotNull
    Short fullStockFactor;
}