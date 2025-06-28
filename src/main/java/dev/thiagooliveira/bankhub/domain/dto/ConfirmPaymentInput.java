package dev.thiagooliveira.bankhub.domain.dto;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record ConfirmPaymentInput(
    UUID payableReceivableId,
    UUID accountId,
    UUID organizationId,
    Optional<OffsetDateTime> dateTime,
    Optional<String> description) {}
