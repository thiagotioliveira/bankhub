package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "payables_receivables")
public class PayableReceivableEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID templateId;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column(nullable = false)
  private LocalDate dueDateOriginal;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PayableReceivableStatus status;

  @Column private Integer installmentNumber;

  @Column private UUID transactionId;

  public PayableReceivableEntity() {}

  public PayableReceivableEntity(
      UUID id,
      UUID templateId,
      LocalDate dueDate,
      LocalDate dueDateOriginal,
      BigDecimal amount,
      PayableReceivableStatus status,
      Integer installmentNumber,
      UUID transactionId) {
    this.id = id;
    this.templateId = templateId;
    this.dueDate = dueDate;
    this.dueDateOriginal = dueDateOriginal;
    this.amount = amount;
    this.status = status;
    this.installmentNumber = installmentNumber;
    this.transactionId = transactionId;
  }

  public static PayableReceivableEntity from(CreatePayableReceivableEnrichedInput input) {
    var entity = new PayableReceivableEntity();
    entity.id = UUID.randomUUID();
    entity.templateId = input.template().id();
    entity.amount = input.amount();
    entity.dueDate = input.dueDate();
    entity.dueDateOriginal = input.dueDate();
    entity.installmentNumber = input.installmentNumber().orElse(null);
    entity.status = PayableReceivableStatus.PENDING;
    return entity;
  }

  public static PayableReceivableEntity from(PayableReceivable payableReceivable) {
    var entity = new PayableReceivableEntity();
    entity.id = payableReceivable.id();
    entity.templateId = payableReceivable.templateId();
    entity.amount = payableReceivable.amount();
    entity.dueDate = payableReceivable.dueDate();
    entity.installmentNumber = payableReceivable.installmentNumber().orElse(null);
    entity.status = payableReceivable.status();
    entity.transactionId = payableReceivable.paymentId().orElse(null);
    return entity;
  }

  public PayableReceivable toDomain(PayableReceivableTemplateEntity templateEntity) {
    return new PayableReceivable(
        this.id,
        this.templateId,
        templateEntity.getAccountId(),
        templateEntity.getCategoryId(),
        templateEntity.getDescription(),
        this.amount,
        this.dueDate,
        templateEntity.getType(),
        this.status,
        Optional.ofNullable(templateEntity.getFrequency()),
        Optional.ofNullable(this.installmentNumber),
        Optional.ofNullable(templateEntity.getInstallmentTotal()),
        Optional.ofNullable(this.transactionId));
  }

  public PayableReceivableEntity update(Optional<BigDecimal> amount, Optional<LocalDate> dueDate) {
    amount.ifPresent(a -> this.amount = a);
    dueDate.ifPresent(d -> this.dueDate = d);
    return this;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public UUID getTemplateId() {
    return templateId;
  }

  public void setTemplateId(UUID templateId) {
    this.templateId = templateId;
  }

  public LocalDate getDueDateOriginal() {
    return dueDateOriginal;
  }

  public void setDueDateOriginal(LocalDate dueDateOriginal) {
    this.dueDateOriginal = dueDateOriginal;
  }
}
