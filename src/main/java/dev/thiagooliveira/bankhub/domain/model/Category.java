package dev.thiagooliveira.bankhub.domain.model;

import java.util.UUID;

public record Category(UUID id, UUID organizationId, String name, CategoryType type) {}
