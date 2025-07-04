package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id private UUID id;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private UUID bankId;

  @Column(nullable = false)
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Currency currency;

  public AccountEntity() {}

  public AccountEntity(
      UUID id,
      OffsetDateTime createdAt,
      UUID organizationId,
      String name,
      UUID bankId,
      BigDecimal balance,
      Currency currency) {
    this.id = id;
    this.createdAt = createdAt;
    this.organizationId = organizationId;
    this.createdAt = createdAt;
    this.name = name;
    this.bankId = bankId;
    this.balance = balance;
    this.currency = currency;
  }

  public static AccountEntity from(CreateAccountInput input) {
    AccountEntity entity = new AccountEntity();
    entity.id = UUID.randomUUID();
    entity.createdAt = OffsetDateTime.now();
    entity.organizationId = input.organizationId();
    entity.name = input.name();
    entity.bankId = input.bankId();
    entity.balance = BigDecimal.ZERO;
    entity.currency = input.currency();
    return entity;
  }

  public Account toDomain() {
    return new Account(id, name, bankId, organizationId, balance, currency, createdAt);
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getBankId() {
    return bankId;
  }

  public void setBankId(UUID bankId) {
    this.bankId = bankId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
