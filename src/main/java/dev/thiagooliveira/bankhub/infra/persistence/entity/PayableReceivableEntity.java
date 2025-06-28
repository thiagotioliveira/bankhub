package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableStatus;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "payables_receivables")
public class PayableReceivableEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PayableReceivableType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PayableReceivableStatus status;

  @Column private Integer installmentNumber;

  @Column private Integer installmentTotal;

  @Column private UUID parentId;

  @Column(nullable = false)
  private UUID categoryId;

  @Column(name = "is_recurring")
  private boolean recurring;

  @Column
  @Enumerated(EnumType.STRING)
  private Frequency frequency;

  @Column private Integer recurrenceDay;

  @Column private UUID transactionId;

  public PayableReceivableEntity() {}

  public PayableReceivableEntity(
      UUID id,
      UUID accountId,
      String description,
      LocalDate dueDate,
      BigDecimal amount,
      PayableReceivableType type,
      PayableReceivableStatus status,
      Integer installmentNumber,
      Integer installmentTotal,
      UUID parentId,
      UUID categoryId,
      boolean recurring,
      Frequency frequency,
      Integer recurrenceDay,
      UUID transactionId) {
    this.id = id;
    this.accountId = accountId;
    this.description = description;
    this.dueDate = dueDate;
    this.amount = amount;
    this.type = type;
    this.status = status;
    this.installmentNumber = installmentNumber;
    this.installmentTotal = installmentTotal;
    this.parentId = parentId;
    this.categoryId = categoryId;
    this.recurring = recurring;
    this.frequency = frequency;
    this.recurrenceDay = recurrenceDay;
    this.transactionId = transactionId;
  }

  public static PayableReceivableEntity from(CreatePayableReceivableEnrichedInput input) {
    var entity = new PayableReceivableEntity();
    entity.id = UUID.randomUUID();
    entity.accountId = input.accountId();
    entity.categoryId = input.category().id();
    entity.amount = input.amount();
    entity.dueDate = input.dueDate();
    entity.frequency = input.frequency().orElse(null);
    entity.installmentNumber = input.installmentNumber().orElse(null);
    entity.installmentTotal = input.installmentTotal().orElse(null);
    entity.description = input.description();
    entity.type = input.type();
    entity.status = PayableReceivableStatus.PENDING;
    entity.recurrenceDay = input.dueDate().getDayOfMonth();
    entity.parentId = null;
    entity.transactionId = null;
    return entity;
  }

  public static PayableReceivableEntity from(PayableReceivable payableReceivable) {
    var entity = new PayableReceivableEntity();
    entity.id = payableReceivable.id();
    entity.accountId = payableReceivable.accountId();
    entity.categoryId = payableReceivable.categoryId();
    entity.amount = payableReceivable.amount();
    entity.dueDate = payableReceivable.dueDate();
    entity.frequency = payableReceivable.frequency().orElse(null);
    entity.installmentNumber = payableReceivable.installmentNumber().orElse(null);
    entity.installmentTotal = payableReceivable.installmentTotal().orElse(null);
    entity.description = payableReceivable.description();
    entity.type = payableReceivable.type();
    entity.status = payableReceivable.status();
    entity.recurrenceDay = payableReceivable.dueDate().getDayOfMonth();
    entity.parentId = null;
    entity.transactionId = payableReceivable.transactionId().orElse(null);
    return entity;
  }

  public PayableReceivable toDomain() {
    return new PayableReceivable(
        this.id,
        this.accountId,
        this.categoryId,
        this.description,
        this.amount,
        this.dueDate,
        this.type,
        this.status,
        Optional.ofNullable(this.frequency),
        Optional.ofNullable(this.installmentNumber),
        Optional.ofNullable(this.installmentTotal),
        Optional.ofNullable(this.transactionId));
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public PayableReceivableType getType() {
    return type;
  }

  public void setType(PayableReceivableType type) {
    this.type = type;
  }

  public PayableReceivableStatus getStatus() {
    return status;
  }

  public void setStatus(PayableReceivableStatus status) {
    this.status = status;
  }

  public Integer getInstallmentNumber() {
    return installmentNumber;
  }

  public void setInstallmentNumber(Integer installmentNumber) {
    this.installmentNumber = installmentNumber;
  }

  public Integer getInstallmentTotal() {
    return installmentTotal;
  }

  public void setInstallmentTotal(Integer installmentTotal) {
    this.installmentTotal = installmentTotal;
  }

  public UUID getParentId() {
    return parentId;
  }

  public void setParentId(UUID parentId) {
    this.parentId = parentId;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  public Frequency getFrequency() {
    return frequency;
  }

  public void setFrequency(Frequency frequency) {
    this.frequency = frequency;
  }

  public Integer getRecurrenceDay() {
    return recurrenceDay;
  }

  public void setRecurrenceDay(Integer recurrenceDay) {
    this.recurrenceDay = recurrenceDay;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public void setRecurring(boolean recurring) {
    this.recurring = recurring;
  }
}
