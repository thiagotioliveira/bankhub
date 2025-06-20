package dev.thiagooliveira.bankhub.domain;

import java.util.UUID;

public record Account(UUID id, String name, UUID bankId, UUID organizationId, int balance) {}
