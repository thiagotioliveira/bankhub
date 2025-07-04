package dev.thiagooliveira.bankhub.domain.dto;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record CreatePaymentInput(
    UUID payableReceivableId,
    UUID organizationId,
    Optional<OffsetDateTime> dateTime,
    Optional<String> description) {}
