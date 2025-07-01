package dev.thiagooliveira.bankhub.domain.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreatePaymentInput(
    UUID payableReceivableId, UUID transactionId, OffsetDateTime dateTime) {}
