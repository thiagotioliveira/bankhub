package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableTemplateInput;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableTemplate;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "payable_receivable_templates")
public class PayableReceivableTemplateEntity {

  @Id private UUID id;

  @Column(name = "account_id", nullable = false)
  private UUID accountId;

  @Column(name = "category_id", nullable = false)
  private UUID categoryId;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private PayableReceivableType type;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "recurring", nullable = false)
  private boolean recurring;

  @Enumerated(EnumType.STRING)
  @Column(name = "frequency")
  private Frequency frequency;

  @Column(name = "installment_total")
  private Integer installmentTotal;

  public PayableReceivableTemplateEntity() {}

  public static PayableReceivableTemplateEntity from(CreatePayableReceivableTemplateInput input) {
    PayableReceivableTemplateEntity entity = new PayableReceivableTemplateEntity();
    entity.setId(UUID.randomUUID());
    entity.setAccountId(input.accountId());
    entity.setCategoryId(input.categoryId());
    entity.setDescription(input.description());
    entity.setAmount(input.amount());
    entity.setType(input.type());
    entity.setStartDate(input.startDate());
    entity.setRecurring(input.recurring());
    entity.setFrequency(input.frequency().orElse(null));
    entity.setInstallmentTotal(input.installmentTotal().orElse(null));
    return entity;
  }

  public PayableReceivableTemplate toDomain() {
    return new PayableReceivableTemplate(
        id,
        accountId,
        categoryId,
        description,
        amount,
        type,
        startDate,
        recurring,
        frequency != null ? Optional.of(frequency) : Optional.empty(),
        installmentTotal != null ? Optional.of(installmentTotal) : Optional.empty());
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

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public void setRecurring(boolean recurring) {
    this.recurring = recurring;
  }

  public Frequency getFrequency() {
    return frequency;
  }

  public void setFrequency(Frequency frequency) {
    this.frequency = frequency;
  }

  public Integer getInstallmentTotal() {
    return installmentTotal;
  }

  public void setInstallmentTotal(Integer installmentTotal) {
    this.installmentTotal = installmentTotal;
  }
}
