package dev.thiagooliveira.bankhub.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransactionInput(
    UUID accountId, OffsetDateTime dateTime, String description, BigDecimal amount) {}
