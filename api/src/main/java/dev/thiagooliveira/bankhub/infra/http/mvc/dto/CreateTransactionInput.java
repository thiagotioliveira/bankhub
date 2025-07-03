package dev.thiagooliveira.bankhub.infra.http.mvc.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateTransactionInput {
  private String type;
  private BigDecimal amount = BigDecimal.TEN;
  private UUID accountId;
  private UUID categoryId;
  private String description;

  public CreateTransactionInput(String type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
