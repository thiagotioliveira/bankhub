package dev.thiagooliveira.bankhub.domain.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrganizationRegistrationOutput(UUID id, OffsetDateTime createdAt, UUID userOwner) {}
