package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    private List<Product> products = new ArrayList<>();

    @Override
    public Optional<Product> findById(UUID uuid) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getUuid().equals(uuid))
                .findFirst();
        return product;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product save(Product product) {
        UUID uuid = UUID.randomUUID();
        product.setUuid(uuid);
        products.add(product);
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        products.removeIf(product -> product.getUuid().equals(uuid));
    }
}
