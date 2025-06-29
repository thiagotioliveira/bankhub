package dev.thiagooliveira.bankhub.domain.dto;

import java.util.UUID;

public record CreateBankInput(UUID organizationId, String name) {}
