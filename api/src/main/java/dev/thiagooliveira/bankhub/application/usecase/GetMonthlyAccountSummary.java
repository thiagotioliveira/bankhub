package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.domain.model.*;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetMonthlyAccountSummary {

  private final TransactionService transactionService;
  private final PayableReceivableService payableReceivableService;

  public GetMonthlyAccountSummary(
      TransactionService transactionService, PayableReceivableService payableReceivableService) {
    this.transactionService = transactionService;
    this.payableReceivableService = payableReceivableService;
  }

  public List<MonthlyAccountSummary> get(UUID organizationId, YearMonth month) {
    var from = month.atDay(1);
    var to = month.atEndOfMonth();
    var payablesReceivables =
        payableReceivableService.findByOrganizationId(organizationId, from, to);
    var transactions = getByOrganizationId(organizationId, from, to);

    var receivables =
        sumByCurrency(
            payablesReceivables, PayableReceivableType.RECEIVABLE, PayableReceivableStatus.PENDING);
    var payables =
        sumByCurrency(
            payablesReceivables, PayableReceivableType.PAYABLE, PayableReceivableStatus.PENDING);
    var creditTransactions = sumByCurrency(transactions, CategoryType.CREDIT);
    var debitTransactions = sumByCurrency(transactions, CategoryType.DEBIT);

    Set<Currency> currencies =
        Stream.of(receivables, payables, creditTransactions, debitTransactions)
            .flatMap(map -> map.keySet().stream())
            .collect(Collectors.toSet());

    return currencies.stream()
        .map(
            currency -> {
              BigDecimal receivablePending = receivables.getOrDefault(currency, BigDecimal.ZERO);
              BigDecimal income =
                  receivablePending.add(creditTransactions.getOrDefault(currency, BigDecimal.ZERO));
              BigDecimal payablePending = payables.getOrDefault(currency, BigDecimal.ZERO);
              BigDecimal expenses =
                  payablePending.add(debitTransactions.getOrDefault(currency, BigDecimal.ZERO));
              return new MonthlyAccountSummary(
                  UUID.randomUUID(), // TODO
                  month,
                  currency,
                  BigDecimal.ZERO,
                  income,
                  expenses,
                  receivablePending,
                  payablePending);
            })
        .collect(Collectors.toList());
  }

  public List<TransactionEnriched> getByOrganizationId(
      UUID organizationId, LocalDate from, LocalDate to) {
    return this.transactionService.getByOrganizationId(organizationId, from, to);
  }

  private Map<Currency, BigDecimal> sumByCurrency(
      List<PayableReceivableEnriched> list,
      PayableReceivableType type,
      PayableReceivableStatus status) {
    return list.stream()
        .filter(p -> type.equals(p.type()) && status.equals(p.status()))
        .collect(
            Collectors.groupingBy(
                PayableReceivableEnriched::currency,
                Collectors.reducing(
                    BigDecimal.ZERO, PayableReceivableEnriched::amount, BigDecimal::add)));
  }

  private Map<Currency, BigDecimal> sumByCurrency(
      List<TransactionEnriched> list, CategoryType categoryType) {
    return list.stream()
        .filter(t -> categoryType.equals(t.category().type()))
        .collect(
            Collectors.groupingBy(
                TransactionEnriched::currency,
                Collectors.reducing(
                    BigDecimal.ZERO, TransactionEnriched::amount, BigDecimal::add)));
  }
}
