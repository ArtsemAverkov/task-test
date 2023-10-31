package ru.clevertec.product.common.extensions.productDto;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ValidProductDtoParameterResolver implements ParameterResolver {

    static BigDecimal price = new BigDecimal("999.9");
    public static List<ProductDto> validProduct = Arrays.asList(
            new ProductDto( "Iphone", "XR", price),
            new ProductDto( "Iphone", "11", price),
            new ProductDto( "Iphone", "12", price)

    );


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== ProductDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validProduct.get(new Random().nextInt(validProduct.size()));
    }
}