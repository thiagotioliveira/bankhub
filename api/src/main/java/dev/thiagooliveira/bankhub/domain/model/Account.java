package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Account(
    UUID id,
    String name,
    UUID bankId,
    UUID organizationId,
    BigDecimal balance,
    Currency currency,
    OffsetDateTime createdAt) {}
