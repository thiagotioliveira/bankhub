package dev.thiagooliveira.bankhub.domain.model;

import java.util.UUID;

public record User(UUID id, String email, String name, UUID organizationId) {}
