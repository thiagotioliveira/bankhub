package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInputExtraInfo;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private OffsetDateTime dateTime;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private UUID categoryId;

  @Column(nullable = false)
  private BigDecimal amount;

  public TransactionEntity() {}

  public TransactionEntity(
      UUID id,
      UUID accountId,
      OffsetDateTime dateTime,
      String description,
      UUID categoryId,
      BigDecimal amount) {
    this.id = id;
    this.accountId = accountId;
    this.dateTime = dateTime;
    this.description = description;
    this.categoryId = categoryId;
    this.amount = amount;
  }

  public static TransactionEntity from(
      CreateTransactionInput input, CreateTransactionInputExtraInfo extraInfo) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = UUID.randomUUID();
    entity.dateTime = input.dateTime();
    entity.accountId = input.accountId();
    entity.categoryId = extraInfo.categoryId();
    entity.description = input.description();
    entity.amount = input.amount();
    return entity;
  }

  public Transaction toDomain() {
    return new Transaction(id, accountId, dateTime, description, categoryId, amount);
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

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
