package ru.clevertec.product.common.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.impl.ProductMapperImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductMapperImplTest {

    @InjectMocks
    private ProductMapperImpl productMapper;

    private static UUID uuidId;

    @BeforeAll
    public static void setUp() {
        uuidId = UUID.randomUUID();
    }
    @ParameterizedTest
    @CsvSource({
            "iPhone, High-end smartphone, 999.9",
    })
    public void testToProduct(String name, String description, String priceString) {
        BigDecimal price = new BigDecimal(priceString);
        ProductDto productDto = new ProductDto(name, description, price);

        Product product = productMapper.toProduct(productDto);

        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "Laptop, Powerful laptop, 1499.99",
    })
    public void testInfoProduct(String name, String description, String priceString){
        BigDecimal price = new BigDecimal(priceString);

        Product product = createProduct(uuidId, name, description, price);
        InfoProductDto infoProductDto = productMapper.toInfoProductDto(product);

        assertEquals(uuidId, infoProductDto.uuid());
        assertEquals(name, infoProductDto.name());
        assertEquals(description, infoProductDto.description());
        assertEquals(price, infoProductDto.price());
    }

    @ParameterizedTest
    @CsvSource({
            "Headphones, Premium headphones, 199.99"
    })
    public void testMerge(String name, String description, String priceString){
        BigDecimal price = new BigDecimal(priceString);
        ProductDto productDto = new ProductDto(name, description, price);

        Product product = createProduct(uuidId, name, description, price);

        Product merge = productMapper.merge(product, productDto);

        assertEquals(uuidId, merge.getUuid());
        assertEquals(name, merge.getName());
        assertEquals(description, merge.getDescription());
        assertEquals(price, merge.getPrice());

    }

    private Product createProduct(UUID uuidId, String name, String description, BigDecimal price) {
        LocalDateTime now = LocalDateTime.now();
        return new Product(uuidId, name, description, price, now);
    }
}

