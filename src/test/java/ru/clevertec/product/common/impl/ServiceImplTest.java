package ru.clevertec.product.common.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.PotentialStubbingProblem;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.common.extensions.infoProduct.ValidInfoProductParameterResolver;
import ru.clevertec.product.common.extensions.product.ValidProductParameterResolver;
import ru.clevertec.product.common.extensions.productDto.ValidProductDtoParameterResolver;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.impl.ProductServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ServiceImplTest {

    @Nested
    @ExtendWith({MockitoExtension.class,
            ValidProductParameterResolver.class,
            ValidInfoProductParameterResolver.class,
            ValidProductDtoParameterResolver.class})
    public class ValidData {

        @InjectMocks
        private ProductServiceImpl productService;

        @Mock
        private ProductRepository productRepository;

        @Mock
        private ProductMapper productMapper;

        @Test
        void shouldReadProductWhenProductValid(Product product, InfoProductDto infoProductDto) {
            UUID uuid = UUID.randomUUID();
            when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
            when(productMapper.toInfoProductDto(product)).thenReturn(infoProductDto);
            assertEquals(infoProductDto, productService.get(uuid));
            verify(productRepository, times(1)).findById(uuid);
        }

        @Test
        void shouldCreateProductWhenProductValid(Product product, ProductDto productDto){
            when(productRepository.save(product)).thenReturn(product);
            when(productMapper.toProduct(productDto)).thenReturn(product);
            assertEquals(product.getUuid(), productService.create(productDto));
            verify(productRepository, times(1)).save(product);
        }

        @Test
        void shouldUpdateProductWhenProductValid(Product product, ProductDto productDto){
            when(productRepository.findById(product.getUuid())).thenReturn(Optional.ofNullable(product));
            when(productMapper.merge(product, productDto)).thenReturn(product);
            productService.update(product.getUuid(), productDto);
            verify(productRepository, times(1)).save(product);
        }

        @Test
        void shouldDeleteProductWhenProductValid(Product product, ProductDto productDto){
            when(productRepository.findById(product.getUuid())).thenReturn(Optional.ofNullable(product));
            productService.delete(product.getUuid());
            verify(productRepository, times(1)).delete(product.getUuid());
        }

        @Test
        void shouldGetAllProductWhenProductValid(Product product, InfoProductDto infoProductDto){
            List<Product> productList = Collections.singletonList(product);
            List<InfoProductDto> infoProductList = Collections.singletonList(infoProductDto);
            when(productRepository.findAll()).thenReturn(productList);
            when(productMapper.toInfoProductDto(product)).thenReturn(infoProductDto);
            assertEquals(infoProductList, productService.getAll());
            verify(productRepository, times(1)).findAll();
        }

    }

    @Nested
    @ExtendWith({MockitoExtension.class,
            ValidProductParameterResolver.class,
            ValidInfoProductParameterResolver.class,
            ValidProductDtoParameterResolver.class})
    public class InValidData {

        @InjectMocks
        private ProductServiceImpl productService;

        @Mock
        private ProductRepository productRepository;

        @Mock
        private ProductMapper productMapper;

        @Test
        void shouldReadProductWhenProductInValid() {
            UUID uuid = UUID.randomUUID();
            when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(null));
            assertThrows(IllegalArgumentException.class, () -> productService.get(uuid));
            verify(productRepository, times(1)).findById(uuid);
        }

        @Test
        void shouldCreateProductWhenProductInValid(Product product, ProductDto productDto){
            when(productMapper.toProduct(productDto)).thenReturn(product);
            when(productRepository.save(product)).thenReturn(null);
            assertThrows(NullPointerException.class, () ->  productService.create(productDto));
            verify(productRepository, times(1)).save(product);
        }

        @Test
        void shouldUpdateProductWhenProductInValid(Product product, ProductDto productDto){
            when(productRepository.findById(null)).thenReturn(Optional.ofNullable(null));
            assertThrows(PotentialStubbingProblem.class, () -> productService.update(product.getUuid(), productDto));
            verify(productRepository, times(1)).findById(product.getUuid());
        }
    }
}
