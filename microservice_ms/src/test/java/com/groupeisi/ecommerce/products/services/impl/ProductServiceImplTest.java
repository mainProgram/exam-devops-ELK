package com.groupeisi.ecommerce.products.services.impl;

import com.groupeisi.ecommerce.exception.EntityExistsException;
import com.groupeisi.ecommerce.products.dto.requests.ProductDtoRequest;
import com.groupeisi.ecommerce.products.dto.responses.ProductDtoResponse;
import com.groupeisi.ecommerce.products.entities.ProductEntity;
import com.groupeisi.ecommerce.products.mapper.ProductsMapper;
import com.groupeisi.ecommerce.products.repository.ProductRepository;
import com.groupeisi.ecommerce.products.services.ProductService;
import org.junit.jupiter.api.*;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductsMapper productsMapper;
    @Mock
    private MessageSource messageSource;


    @Test
    void saveProductOK() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.empty());
        when(productsMapper.toProductEntity(any())).thenReturn(this.getProductEntity());
        when(productRepository.save(any())).thenReturn(this.getProductEntity());
        when(productsMapper.toProductDtoResponse(any())).thenReturn(this.getProductDtoResponse());

        Optional<ProductDtoResponse> productDtoResponse1 = productService.saveProduct(this.getProductDtoRequest());
        assertTrue(productDtoResponse1.isPresent());
    }

    @Test
    void saveProductKO() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.of(this.getProductEntity()));
        when(messageSource.getMessage(eq("product.exists"), any(), any(Locale.class))).thenReturn("the product with ref = MAD01 is already created");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> productService.saveProduct(this.getProductDtoRequest()));
        assertEquals("the product with ref = MAD01 is already created", exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getProductByRef() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }

    private ProductDtoRequest getProductDtoRequest(){
        ProductDtoRequest productDtoRequest = new ProductDtoRequest();
        productDtoRequest.setRef("MAD01");
        productDtoRequest.setName("Madar");
        productDtoRequest.setStock(100.0);
        productDtoRequest.setIdUser(1);

        return productDtoRequest;
    }

    private ProductEntity getProductEntity(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setRef("MAD01");
        productEntity.setName("Madar");
        productEntity.setStock(100.0);
        productEntity.setIdUser(1);

        return productEntity;
    }

    private ProductDtoResponse getProductDtoResponse(){
        ProductDtoResponse productDtoResponse = new ProductDtoResponse();
        productDtoResponse.setRef("MAD01");
        productDtoResponse.setName("Madar");
        productDtoResponse.setStock(100.0);
        productDtoResponse.setIdUser(1);

        return  productDtoResponse;
    }
}