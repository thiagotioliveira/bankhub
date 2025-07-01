package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record PayableReceivableTemplate(
    UUID id,
    UUID accountId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    PayableReceivableType type,
    LocalDate startDate,
    boolean recurring,
    Optional<Frequency> frequency,
    Optional<Integer> installmentTotal) {}
