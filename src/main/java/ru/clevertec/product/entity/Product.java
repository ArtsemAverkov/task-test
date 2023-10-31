package ru.clevertec.product.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Product {

    /**
     * Идентификатор продукта (генерируется базой)
     */
    private UUID uuid;

    /**
     * Название продукта (не может быть null или пустым, содержит 5-10 символов(русский или пробелы))
     */
    @NotEmpty(message = "Название продукта не может быть пустым")
    @Size(min = 5, max = 10, message = "Название продукта должно содержать от 5 до 10 символов")
    @Pattern(regexp = "[а-яА-Я\\s]+", message = "Название продукта должно содержать только русские буквы и пробелы")
    private String name;

    /**
     * Описание продукта(может быть null или 10-30 символов(русский и пробелы))
     */
    @Size(min = 10, max = 30, message = "Описание продукта должно содержать от 10 до 30 символов")
    @Pattern(regexp = "[а-яА-Я\\s]+", message = "Описание продукта должно содержать только русские буквы и пробелы")
    private String description;

    /**
     * Не может быть null и должен быть положительным
     */
    @NotNull(message = "Цена не может быть пустой")
    @DecimalMin(value = "0.01", message = "Цена должна быть положительной и больше 0")
    private BigDecimal price;

    /**
     * Время создания, не может быть null(задаётся до сохранения и не обновляется)
     */
    @NotNull(message = "Время создания не может быть пустым")
    private LocalDateTime created;
}
