package ru.clevertec.product.mapper.impl;

import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

import java.math.BigDecimal;

public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        // Используем Lombok Builder для создания объекта Product
        return Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();
    }

    public InfoProductDto toInfoProductDto(Product product) {
        if (product == null) {
            return null;
        }

        return new InfoProductDto(
                product.getUuid(),
                product.getName(),
                product.getDescription(),
                product.getPrice().compareTo(BigDecimal.ZERO) > 0 ? product.getPrice() : BigDecimal.ZERO);
    }

    @Override
    public Product merge(Product product, ProductDto productDto) {
        if (product == null || productDto == null) {
            return null;
        }

        return Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();
    }
}
