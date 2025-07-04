package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.*;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Stream;

public class GetMonthlyAccountSummary {

  private final TransactionService transactionService;
  private final PayableReceivableService payableReceivableService;
  private final AccountPort accountPort;

  public GetMonthlyAccountSummary(
      TransactionService transactionService,
      PayableReceivableService payableReceivableService,
      AccountPort accountPort) {
    this.transactionService = transactionService;
    this.payableReceivableService = payableReceivableService;
    this.accountPort = accountPort;
  }

  public Optional<MonthlyAccountSummary> get(UUID accountId, YearMonth month) {
    Account account =
        accountPort
            .findById(accountId)
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));

    YearMonth createdAt = YearMonth.from(account.createdAt());
    if (month.isBefore(createdAt)) return Optional.empty();

    if (month.equals(createdAt)) {
      return calculateMonthlySummary(accountId, month, null);
    }

    Optional<MonthlyAccountSummary> lastSummaryOpt = accountPort.getLastAccountSummary(accountId);
    if (lastSummaryOpt.isEmpty()) throw new RuntimeException("need to check");

    MonthlyAccountSummary lastSummary = lastSummaryOpt.get();
    List<YearMonth> monthsToCalculate =
        generateMonths(lastSummary.yearMonth().plusMonths(1), month);

    BigDecimal balance = lastSummary.balance();
    for (YearMonth currentMonth : monthsToCalculate) {
      MonthlySummaryData data = fetchSummaryData(accountId, currentMonth);
      balance =
          balance.add(
              data.income
                  .subtract(data.expenses)
                  .add(data.receivablePending.subtract(data.payablePending)));

      if (currentMonth.equals(month)) {
        return Optional.of(
            new MonthlyAccountSummary(
                accountId,
                month,
                balance,
                data.income,
                data.expenses,
                data.receivablePending,
                data.payablePending));
      }
    }

    return Optional.empty();
  }

  private Optional<MonthlyAccountSummary> calculateMonthlySummary(
      UUID accountId, YearMonth month, BigDecimal previousBalance) {
    MonthlySummaryData data = fetchSummaryData(accountId, month);

    boolean isEmpty = data.transactions.isEmpty() && data.expectedTransactions.isEmpty();
    if (previousBalance == null && isEmpty) return Optional.empty();

    BigDecimal balance =
        previousBalance != null
            ? previousBalance.add(
                data.income
                    .subtract(data.expenses)
                    .add(data.receivablePending.subtract(data.payablePending)))
            : data.income.subtract(data.expenses);

    return Optional.of(
        new MonthlyAccountSummary(
            accountId,
            month,
            balance,
            data.income,
            data.expenses,
            data.receivablePending,
            data.payablePending));
  }

  private MonthlySummaryData fetchSummaryData(UUID accountId, YearMonth month) {
    var from = month.atDay(1);
    var to = month.atEndOfMonth();

    List<PayableReceivableEnriched> expectedTransactions =
        payableReceivableService.getByAccountIdOrderByDueDateAsc(accountId, from, to);
    List<TransactionEnriched> transactions =
        transactionService.getByAccountIdOrderByDateTime(accountId, from, to);

    BigDecimal receivablePending =
        sumByTypeAndStatus(
            expectedTransactions,
            PayableReceivableType.RECEIVABLE,
            PayableReceivableStatus.PENDING);
    BigDecimal payablePending =
        sumByTypeAndStatus(
            expectedTransactions, PayableReceivableType.PAYABLE, PayableReceivableStatus.PENDING);
    BigDecimal income = sumByCategoryType(transactions, CategoryType.CREDIT);
    BigDecimal expenses = sumByCategoryType(transactions, CategoryType.DEBIT);

    return new MonthlySummaryData(
        transactions, expectedTransactions, income, expenses, receivablePending, payablePending);
  }

  private BigDecimal sumByTypeAndStatus(
      List<PayableReceivableEnriched> list,
      PayableReceivableType type,
      PayableReceivableStatus status) {
    return list.stream()
        .filter(p -> type.equals(p.type()) && status.equals(p.status()))
        .map(PayableReceivableEnriched::amount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal sumByCategoryType(List<TransactionEnriched> list, CategoryType categoryType) {
    return list.stream()
        .filter(t -> categoryType.equals(t.category().type()))
        .map(TransactionEnriched::amount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private List<YearMonth> generateMonths(YearMonth start, YearMonth end) {
    return Stream.iterate(start, ym -> !ym.isAfter(end), ym -> ym.plusMonths(1)).toList();
  }

  private record MonthlySummaryData(
      List<TransactionEnriched> transactions,
      List<PayableReceivableEnriched> expectedTransactions,
      BigDecimal income,
      BigDecimal expenses,
      BigDecimal receivablePending,
      BigDecimal payablePending) {}
}
