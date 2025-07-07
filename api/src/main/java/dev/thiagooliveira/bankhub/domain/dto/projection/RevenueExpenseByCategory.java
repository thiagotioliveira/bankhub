package dev.thiagooliveira.bankhub.domain.dto.projection;

import java.math.BigDecimal;
import java.util.UUID;

public record RevenueExpenseByCategory(
    UUID categoryId, String categoryName, String type, BigDecimal amount) {}
