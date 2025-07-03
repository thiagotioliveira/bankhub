package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;

public record IncomeAndExpenses(
    Currency currency,
    BigDecimal income,
    BigDecimal expenses,
    BigDecimal receivablesPending,
    BigDecimal payablePending) {}
