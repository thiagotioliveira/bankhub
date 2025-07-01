package dev.thiagooliveira.bankhub.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record UpdatePayableReceivableInput(
    UUID payableReceivableId,
    UUID organizationId,
    Optional<BigDecimal> amount,
    Optional<LocalDate> dueDate) {}
