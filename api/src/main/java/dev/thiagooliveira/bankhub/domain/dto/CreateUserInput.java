package dev.thiagooliveira.bankhub.domain.dto;

import java.util.UUID;

public record CreateUserInput(String name, String email, UUID organizationId) {}
