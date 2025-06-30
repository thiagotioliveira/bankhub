package dev.thiagooliveira.bankhub.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "account_balance_snapshots")
public class AccountBalanceSnapshotEntity {
  @Id private UUID id;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false, name = "at_date")
  private LocalDate date;

  @Column(nullable = false)
  private BigDecimal balance;

  public AccountBalanceSnapshotEntity() {}

  public AccountBalanceSnapshotEntity(UUID id, UUID accountId, LocalDate date, BigDecimal balance) {
    this.id = id;
    this.accountId = accountId;
    this.date = date;
    this.balance = balance;
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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
