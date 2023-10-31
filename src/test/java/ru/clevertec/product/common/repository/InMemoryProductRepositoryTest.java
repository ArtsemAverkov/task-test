package ru.clevertec.product.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.impl.InMemoryProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryProductRepository();
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, High-end smartphone, 999.9")
    public void testSaveProduct(UUID id,String name, String description, BigDecimal price) {
        Product product = new Product(id, name, description, price, LocalDateTime.now());

        Product savedProduct = repository.save(product);
        assertNotNull(savedProduct.getUuid());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getCreated(), savedProduct.getCreated());
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, High-end smartphone, 999.9")
    public void testFindProductById(UUID id,String name, String description, BigDecimal price) {
        Product product = new Product(id, name, description, price, LocalDateTime.now());
        repository.save(product);
        Optional<Product> foundProduct = repository.findById(id);
        assertTrue(foundProduct.isPresent());
        assertEquals(id, foundProduct.get().getUuid());
    }

    @Test
    public void testFindProductByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        Optional<Product> foundProduct = repository.findById(uuid);
        assertTrue(foundProduct.isEmpty());
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, High-end smartphone, 999.9")
    public void testFindAllProducts(UUID id,String name, String description, BigDecimal price) {
        Product product = new Product(id, name, description, price, LocalDateTime.now());
        repository.save(product);
        List<Product> allProducts = repository.findAll();
        assertEquals(3, allProducts.size());
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, High-end smartphone, 999.9")
    public void testDeleteProduct(UUID id,String name, String description, BigDecimal price) {
        Product product = new Product(id, name, description, price, LocalDateTime.now());
        UUID uuid = repository.save(product).getUuid();
        repository.delete(uuid);
        Optional<Product> deletedProduct = repository.findById(uuid);
        assertTrue(deletedProduct.isEmpty());
    }
}


