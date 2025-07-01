package dev.thiagooliveira.bankhub.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record ConfirmPaymentInput(
    UUID payableReceivableId,
    UUID organizationId,
    Optional<OffsetDateTime> dateTime,
    Optional<String> description,
    Optional<BigDecimal> amount) {}
