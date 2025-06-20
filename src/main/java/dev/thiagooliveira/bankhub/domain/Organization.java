package dev.thiagooliveira.bankhub.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Organization(UUID id, OffsetDateTime createdAt, String emailOwner) {}
