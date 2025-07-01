package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreatePayableReceivableTemplateEnrichedInput(
    UUID accountId,
    UUID organizationId,
    Category category,
    String description,
    BigDecimal amount,
    PayableReceivableType type,
    LocalDate startDate,
    boolean recurring,
    Optional<Frequency> frequency,
    Optional<Integer> installmentTotal) {}
