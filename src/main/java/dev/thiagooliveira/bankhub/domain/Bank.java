package dev.thiagooliveira.bankhub.domain;

import java.util.UUID;

public record Bank(UUID id, String name, UUID organizationId) {}
