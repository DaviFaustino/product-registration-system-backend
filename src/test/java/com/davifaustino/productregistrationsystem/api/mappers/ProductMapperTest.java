package com.davifaustino.productregistrationsystem.api.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.davifaustino.productregistrationsystem.api.dtos.requests.ProductRequest;
import com.davifaustino.productregistrationsystem.api.dtos.responses.ProductResponse;
import com.davifaustino.productregistrationsystem.business.entities.Product;
import com.davifaustino.productregistrationsystem.config.ModelMapperConfig;

@SpringBootTest(classes = {ProductMapper.class, ModelMapperConfig.class})
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    void testToEntity() {
        ProductRequest productRequest = new ProductRequest("1111111111111", "a", "a", "a", 1, 1, true);

        Product mapperResponse = productMapper.toEntity(productRequest);

        assertEquals(Product.class, mapperResponse.getClass());
        assertEquals(productRequest.getCode(), mapperResponse.getCode());
        assertEquals(productRequest.getProductTypeName(), mapperResponse.getProductTypeName());
        assertEquals(productRequest.getName(), mapperResponse.getName());
        assertEquals(productRequest.getDescription(), mapperResponse.getDescription());
        assertEquals(productRequest.getPurchasePriceInCents(), mapperResponse.getPurchasePriceInCents());
        assertEquals(productRequest.getSalePriceInCents(), mapperResponse.getSalePriceInCents());
        assertEquals(productRequest.getFullStock(), mapperResponse.getFullStock());
    }
    
    @Test
    void testToResponse() {
        Product product = new Product("1111111111111", "a", "a", "a", 1, 1, 1, 1, new Timestamp(1), true);

        ProductResponse mapperResponse = productMapper.toResponse(product);

        assertEquals(product.getCode(), mapperResponse.getCode());
        assertEquals(product.getProductTypeName(), mapperResponse.getProductTypeName());
        assertEquals(product.getName(), mapperResponse.getName());
        assertEquals(product.getDescription(), mapperResponse.getDescription());
        assertEquals(product.getPurchasePriceInCents(), mapperResponse.getPurchasePriceInCents());
        assertEquals(product.getPreviousPurchasePriceInCents(), mapperResponse.getPreviousPurchasePriceInCents());
        assertEquals(product.getSalePriceInCents(), mapperResponse.getPreviousSalePriceInCents());
        assertEquals(product.getPreviousSalePriceInCents(), mapperResponse.getPreviousSalePriceInCents());
        assertEquals(product.getPriceUpdateDate(), mapperResponse.getPriceUpdateDate());
        assertEquals(product.getFullStock(), mapperResponse.getFullStock());
    }
}
