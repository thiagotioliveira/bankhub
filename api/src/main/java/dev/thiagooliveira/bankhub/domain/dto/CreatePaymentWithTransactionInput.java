package dev.thiagooliveira.bankhub.domain.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreatePaymentWithTransactionInput(
    UUID payableReceivableId, UUID transactionId, OffsetDateTime dateTime) {}
