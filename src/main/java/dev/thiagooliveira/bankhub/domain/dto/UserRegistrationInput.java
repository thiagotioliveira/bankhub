package dev.thiagooliveira.bankhub.domain.dto;

import java.util.UUID;

public record UserRegistrationInput(String name, String email, UUID organizationId) {}
