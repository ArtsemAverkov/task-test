package ru.clevertec.product.common.extensions.product;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ValidProductParameterResolver implements ParameterResolver {

    static BigDecimal price = new BigDecimal("999.9");
    static UUID uuid = UUID.randomUUID();
    public static List<Product> validProduct = Arrays.asList(
            new Product(uuid , "Iphone", "XR", price, LocalDateTime.now()),
            new Product(uuid , "Iphone", "11", price, LocalDateTime.now()),
            new Product(uuid , "Iphone", "12", price, LocalDateTime.now())
    );


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== Product.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validProduct.get(new Random().nextInt(validProduct.size()));
    }
}