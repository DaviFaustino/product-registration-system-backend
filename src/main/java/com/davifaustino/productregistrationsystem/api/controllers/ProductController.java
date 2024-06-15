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

import com.davifaustino.productregistrationsystem.api.dtos.requests.ProductRequest;
import com.davifaustino.productregistrationsystem.api.dtos.responses.ErrorResponse;
import com.davifaustino.productregistrationsystem.api.dtos.responses.ProductResponse;
import com.davifaustino.productregistrationsystem.api.mappers.ProductMapper;
import com.davifaustino.productregistrationsystem.business.entities.Product;
import com.davifaustino.productregistrationsystem.business.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
                    content = {@Content(schema = @Schema(implementation =  ProductResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request content",
                    content = {@Content(schema = @Schema(implementation =  ErrorResponse.class))}),
        @ApiResponse(responseCode = "409", description = "Product already exists in the database",
                    content = {@Content(schema = @Schema(implementation =  ErrorResponse.class))})
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> saveProduct (@RequestBody @Valid ProductRequest request) {
        Product serviceResponse = productService.saveProduct(productMapper.toEntity(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toResponse(serviceResponse));
    }

    
    @Operation(summary = "Get a list of products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Response successfully received",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation =  ProductResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Invalid request content",
                    content = {@Content(schema = @Schema(implementation =  ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(defaultValue = "") String searchTerm, @RequestParam(required = false) String productTypeName) {
        List<Product> serviceResponse = productService.getProducts(searchTerm, Optional.ofNullable(productTypeName));

        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponseList(serviceResponse));
    }
}
