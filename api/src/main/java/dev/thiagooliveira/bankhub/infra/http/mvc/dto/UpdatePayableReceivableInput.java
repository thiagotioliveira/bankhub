package dev.thiagooliveira.bankhub.infra.http.mvc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class UpdatePayableReceivableInput {
  private UUID id;
  private boolean paid;
  private LocalDate dueDate;
  private BigDecimal amount;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
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
}
