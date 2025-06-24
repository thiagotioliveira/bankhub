package dev.thiagooliveira.bankhub.domain.model;

import java.util.Optional;
import java.util.UUID;

public record Category(UUID id, Optional<UUID> organizationId, String name, CategoryType type) {}
