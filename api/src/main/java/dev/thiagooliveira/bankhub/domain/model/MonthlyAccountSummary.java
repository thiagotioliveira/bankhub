package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public record MonthlyAccountSummary(
    UUID accountId,
    YearMonth yearMonth,
    Currency currency,
    BigDecimal balance,
    BigDecimal income,
    BigDecimal expenses,
    BigDecimal receivablesPending,
    BigDecimal payablePending) {}
