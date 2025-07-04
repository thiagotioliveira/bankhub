package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.model.MonthlyAccountSummary;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(name = "monthly_account_summary")
public class MonthlyAccountSummaryEntity {
  @Id private UUID id;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private YearMonth yearMonth;

  @Column(nullable = false)
  private BigDecimal balance;

  @Column(nullable = false)
  private BigDecimal income;

  @Column(nullable = false)
  private BigDecimal expenses;

  @Column(nullable = false)
  private BigDecimal receivablesPending;

  @Column(nullable = false)
  private BigDecimal payablePending;

  public MonthlyAccountSummaryEntity() {}

  public MonthlyAccountSummaryEntity(
      UUID id,
      UUID accountId,
      YearMonth yearMonth,
      BigDecimal balance,
      BigDecimal income,
      BigDecimal expenses,
      BigDecimal receivablesPending,
      BigDecimal payablePending) {
    this.id = id;
    this.accountId = accountId;
    this.yearMonth = yearMonth;
    this.balance = balance;
    this.income = income;
    this.expenses = expenses;
    this.receivablesPending = receivablesPending;
    this.payablePending = payablePending;
  }

  public MonthlyAccountSummary toDomain() {
    return new MonthlyAccountSummary(
        this.accountId,
        this.yearMonth,
        this.balance,
        this.income,
        this.expenses,
        this.receivablesPending,
        this.payablePending);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public YearMonth getYearMonth() {
    return yearMonth;
  }

  public void setYearMonth(YearMonth yearMonth) {
    this.yearMonth = yearMonth;
  }

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(BigDecimal income) {
    this.income = income;
  }

  public BigDecimal getExpenses() {
    return expenses;
  }

  public void setExpenses(BigDecimal expenses) {
    this.expenses = expenses;
  }

  public BigDecimal getReceivablesPending() {
    return receivablesPending;
  }

  public void setReceivablesPending(BigDecimal receivablesPending) {
    this.receivablesPending = receivablesPending;
  }

  public BigDecimal getPayablePending() {
    return payablePending;
  }

  public void setPayablePending(BigDecimal payablePending) {
    this.payablePending = payablePending;
  }
}
