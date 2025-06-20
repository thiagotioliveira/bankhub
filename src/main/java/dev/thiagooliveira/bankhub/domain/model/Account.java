package dev.thiagooliveira.bankhub.domain.model;

import java.util.UUID;

public record Account(UUID id, String name, UUID bankId, UUID organizationId, int balance) {}
