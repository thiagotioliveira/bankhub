package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id private UUID id;

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
      UUID organizationId,
      String name,
      UUID bankId,
      BigDecimal balance,
      Currency currency) {
    this.id = id;
    this.organizationId = organizationId;
    this.name = name;
    this.bankId = bankId;
    this.balance = balance;
    this.currency = currency;
  }

  public static AccountEntity from(CreateAccountInput input) {
    AccountEntity entity = new AccountEntity();
    entity.id = UUID.randomUUID();
    entity.organizationId = input.organizationId();
    entity.name = input.name();
    entity.bankId = input.bankId();
    entity.balance = input.balance();
    entity.currency = input.currency();
    return entity;
  }

  public Account toDomain() {
    return new Account(id, name, bankId, organizationId, balance, currency);
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
