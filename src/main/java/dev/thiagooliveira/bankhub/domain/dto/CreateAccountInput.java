package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.util.UUID;

public record CreateAccountInput(
    String name, UUID bankId, UUID organizationId, Currency currency) {}
