package dev.thiagooliveira.bankhub.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Payment(
    UUID id, UUID payableReceivableId, UUID transactionId, OffsetDateTime dateTime) {}
