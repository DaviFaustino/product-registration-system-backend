package com.davifaustino.productregistrationsystem.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davifaustino.productregistrationsystem.api.dtos.ErrorResponseDto;
import com.davifaustino.productregistrationsystem.api.dtos.ProductDto;
import com.davifaustino.productregistrationsystem.api.mappers.ProductMapper;
import com.davifaustino.productregistrationsystem.business.entities.Product;
import com.davifaustino.productregistrationsystem.business.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product saved successfully",
                    content = {@Content(schema = @Schema(implementation =  ProductDto.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request content",
                    content = {@Content(schema = @Schema(implementation =  ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "409", description = "Product already exists in the database",
                    content = {@Content(schema = @Schema(implementation =  ErrorResponseDto.class))})
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> saveProduct (@RequestBody @Valid ProductDto dto) {
        Product response = productService.saveProduct(productMapper.toEntity(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toDto(response));
    }

    @Operation(summary = "Get a list of products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Response successfully received"),
        @ApiResponse(responseCode = "400", description = "Invalid request content")
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(defaultValue = "") String searchTerm, @RequestParam(required = false) String productTypeName) {
        List<Product> response = productService.getProducts(searchTerm, Optional.ofNullable(productTypeName));

        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDtoList(response));
    }
}
