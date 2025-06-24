package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountInput(
    String name, UUID bankId, UUID organizationId, BigDecimal initialBalance, Currency currency) {}
