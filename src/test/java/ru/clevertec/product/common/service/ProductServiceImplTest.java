package ru.clevertec.product.common.service;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;
    private static UUID uuidId;

    @BeforeAll
    public static void setUp() {
        uuidId = UUID.randomUUID();
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, 'High-end smartphone', 999.9")
    public void testGet (UUID uuid, String name, String description, BigDecimal price){
        Product testProduct = new Product(uuid, name, description, price, LocalDateTime.now());
        InfoProductDto expectedInfoProductDto = new InfoProductDto(uuid, name, description, price);

        when(productRepository.findById(uuid)).thenReturn(Optional.of(testProduct));
        when(productMapper.toInfoProductDto(testProduct)).thenReturn(expectedInfoProductDto);

        assertEquals(expectedInfoProductDto, productService.get(uuid));
        verify(productRepository).findById(uuid);
        verify(productMapper).toInfoProductDto(testProduct);
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, 'High-end smartphone', 999.9")
    public void testCreate (UUID uuid, String name, String description, BigDecimal price){
        ProductDto productDto = new ProductDto(name, description, price);
        Product product = new Product(uuid, name, description, price, LocalDateTime.now());

        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProduct(productDto)).thenReturn(product);
        verify(productMapper).toProduct(productDto);
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, 'High-end smartphone', 999.9")
    public void testUpdate (UUID uuid, String name, String description, BigDecimal price){
        ProductDto productDto = new ProductDto(name, description, price);
        Product product = new Product(uuid, name, description, price, LocalDateTime.now());

        when(productRepository.findById(product.getUuid())).thenReturn(Optional.ofNullable(product));
        when(productMapper.merge(product, productDto)).thenReturn(product);
        productService.update(product.getUuid(), productDto);

        verify(productRepository).findById(uuid);
        verify(productMapper).merge(product, productDto);
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, 'High-end smartphone', 999.9")
    public void testDelete (UUID uuid, String name, String description, BigDecimal price){
        Product product = new Product(uuid, name, description, price, LocalDateTime.now());

        when(productRepository.findById(product.getUuid())).thenReturn(Optional.ofNullable(product));
        productService.delete(product.getUuid());

        verify(productRepository).findById(uuid);
    }

    @ParameterizedTest
    @CsvSource("4e3e12ea-cfb9-4a08-9e07-40f083a623ac, iPhone, 'High-end smartphone', 999.9")
    public void testGetAll (UUID uuid, String name, String description, BigDecimal price){
        InfoProductDto expectedInfoProductDto = new InfoProductDto(uuid, name, description, price);
        Product product = new Product(uuid, name, description, price, LocalDateTime.now());

        List<Product> productList = Collections.singletonList(product);
        List<InfoProductDto> infoProductList = Collections.singletonList(expectedInfoProductDto);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toInfoProductDto(product)).thenReturn(expectedInfoProductDto);
        assertEquals(infoProductList, productService.getAll());
        verify(productRepository).findAll();
    }
}
