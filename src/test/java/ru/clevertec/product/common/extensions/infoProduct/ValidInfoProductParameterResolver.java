package ru.clevertec.product.common.extensions.infoProduct;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.product.data.InfoProductDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ValidInfoProductParameterResolver implements ParameterResolver {

    static BigDecimal price = new BigDecimal("999.9");
    static UUID uuid = UUID.randomUUID();
    public static List<InfoProductDto> validProduct = Arrays.asList(
            new InfoProductDto( uuid,"Iphone", "XR", price),
            new InfoProductDto( uuid,"Iphone", "11", price),
            new InfoProductDto( uuid,"Iphone", "12", price)
    );


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== InfoProductDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validProduct.get(new Random().nextInt(validProduct.size()));
    }
}
