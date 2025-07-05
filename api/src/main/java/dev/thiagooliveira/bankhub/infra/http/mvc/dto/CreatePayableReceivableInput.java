package dev.thiagooliveira.bankhub.infra.http.mvc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreatePayableReceivableInput {
  private String type;
  private UUID accountId;
  private UUID categoryId;
  private String description;
  private BigDecimal amount = BigDecimal.TEN;
  private LocalDate startDate;
  private boolean recurring = true;
  private String frequence;
  private Integer installmentTotal;

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

  public String getFrequence() {
    return frequence;
  }

  public void setFrequence(String frequence) {
    this.frequence = frequence;
  }

  public Integer getInstallmentTotal() {
    return installmentTotal;
  }

  public void setInstallmentTotal(Integer installmentTotal) {
    this.installmentTotal = installmentTotal;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
