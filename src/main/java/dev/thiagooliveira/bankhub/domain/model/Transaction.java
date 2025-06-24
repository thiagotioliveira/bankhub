package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Transaction(
    UUID id,
    UUID accountId,
    OffsetDateTime dateTime,
    String description,
    UUID categoryId,
    BigDecimal amount) {}
