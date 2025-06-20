package dev.thiagooliveira.bankhub.domain.model;

import java.util.UUID;

public record Bank(UUID id, UUID organizationId, String name) {}
