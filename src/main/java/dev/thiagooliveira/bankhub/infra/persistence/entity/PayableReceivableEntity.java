package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.Receivable;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableStatus;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

  public static List<PayableReceivableEntity> from(CreateReceivableEnrichedInput input) {
    boolean hasFrequency = input.frequency().isPresent();
    boolean isRecurring = input.recurring();

    if (!hasFrequency || isRecurring) {
      PayableReceivableEntity entity =
          mapToEntity(input, input.dueDate(), Optional.empty(), input.amount());
      return List.of(entity);
    }

    int totalInstallments = input.installmentTotal().orElse(1);
    BigDecimal totalAmount = input.amount();
    BigDecimal[] division = totalAmount.divideAndRemainder(BigDecimal.valueOf(totalInstallments));
    BigDecimal baseAmount = division[0].setScale(2, RoundingMode.DOWN);
    BigDecimal remainder = division[1];

    List<PayableReceivableEntity> entities = new ArrayList<>();
    LocalDate dueDate = input.dueDate();
    Frequency frequency = input.frequency().get();

    for (int i = 1; i <= totalInstallments; i++) {
      BigDecimal amount = baseAmount;
      if (i == totalInstallments) {
        amount = baseAmount.add(remainder);
      }

      entities.add(mapToEntity(input, dueDate, Optional.of(i), amount));

      dueDate =
          switch (frequency) {
            case DAILY -> dueDate.plusDays(1);
            case WEEKLY -> dueDate.plusWeeks(1);
            case MONTHLY -> dueDate.plusMonths(1);
            case YEARLY -> dueDate.plusYears(1);
          };
    }

    return List.copyOf(entities);
  }

  private static PayableReceivableEntity mapToEntity(
      CreateReceivableEnrichedInput input,
      LocalDate dueDate,
      Optional<Integer> installmentNumber,
      BigDecimal amount) {
    var entity = new PayableReceivableEntity();
    entity.id = UUID.randomUUID();
    entity.accountId = input.accountId();
    entity.categoryId = input.category().id();
    entity.amount = amount;
    entity.dueDate = dueDate;
    entity.frequency = input.frequency().orElse(null);
    entity.installmentNumber = installmentNumber.orElse(null);
    entity.installmentTotal = input.installmentTotal().orElse(null);
    entity.description =
        input.description()
            + (input.frequency().isPresent() && input.installmentTotal().isPresent()
                ? " (%d/%d)".formatted(installmentNumber.orElse(1), input.installmentTotal().get())
                : "");
    entity.type = PayableReceivableType.RECEIVABLE;
    entity.status = PayableReceivableStatus.PENDING;
    entity.recurrenceDay = dueDate.getDayOfMonth();
    entity.parentId = null;
    entity.transactionId = null;
    return entity;
  }

  public Receivable toReceivableOutput() {
    return new Receivable(
        this.id,
        this.accountId,
        this.categoryId,
        this.description,
        this.amount,
        this.dueDate,
        this.status,
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
