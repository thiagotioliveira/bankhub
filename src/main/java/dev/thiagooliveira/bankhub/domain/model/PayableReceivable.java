package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record PayableReceivable(
    UUID id,
    UUID accountId,
    UUID categoryId,
    String description,
    OffsetDateTime dueDate,
    BigDecimal amount,
    PayableReceivableType type,
    PayableReceivableStatus status,
    Optional<Integer> installmentNumber,
    Optional<Integer> installmentTotal,
    Optional<UUID> parentId,
    Optional<Frequency> frequency,
    Optional<Integer> recurrenceDay,
    Optional<UUID> transactionId) {}
