package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @SneakyThrows
    @Override
    public InfoProductDto get(UUID uuid) {
        Optional<Product> byId = productRepository.findById(uuid);
        if (byId.isPresent()) {
            InfoProductDto infoProductDto = byId.stream()
                    .map(mapper::toInfoProductDto)
                    .findFirst()
                    .orElse(null);
            return infoProductDto;
        } else {
            throw new IllegalArgumentException("Продукт с таким " + uuid + "не был найден");
        }
    }

    @Override
    public List<InfoProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toInfoProductDto)
                .toList();
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        return Stream.of(product)
                .map(productRepository::save)
                .map(Product::getUuid)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        productRepository.findById(uuid)
                .ifPresent(existingProduct -> {
                    Product updatedProduct = mapper.merge(existingProduct, productDto);
                    productRepository.save(updatedProduct);
                });
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.findById(uuid)
                .ifPresent(existingProduct -> productRepository.delete(uuid));
    }
}
