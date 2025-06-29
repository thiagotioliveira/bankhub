package dev.thiagooliveira.bankhub.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransactionEnrichedInput(
    UUID accountId,
    UUID organizationId,
    OffsetDateTime dateTime,
    String description,
    BigDecimal amount,
    UUID categoryId) {}
