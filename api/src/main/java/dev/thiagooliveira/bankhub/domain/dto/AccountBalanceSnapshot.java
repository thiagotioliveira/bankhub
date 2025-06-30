package dev.thiagooliveira.bankhub.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountBalanceSnapshot(LocalDate date, BigDecimal balance) {}
