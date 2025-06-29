package dev.thiagooliveira.bankhub.util;

import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class TestUtil {

  public static CreateOrganizationInput createOrganizationInput() {
    return new CreateOrganizationInput("Jack Bauer", "j.bauer@bankhub.app");
  }

  public static CreateCategoryInput createCategoryInput(
      Optional<UUID> organizationId, CategoryType categoryType) {
    return new CreateCategoryInput(organizationId, "Category " + categoryType.name(), categoryType);
  }

  public static CreateBankInput createBankInput(UUID organizationId) {
    return new CreateBankInput(organizationId, "Bank " + UUID.randomUUID());
  }

  public static CreateAccountInput createAccountInput(
      UUID organizationId, UUID bankId, Currency currency) {
    return new CreateAccountInput("Account " + UUID.randomUUID(), bankId, organizationId, currency);
  }

  public static CreateTransactionInput createTransactionInput(
      UUID accountId, UUID organizationId, OffsetDateTime dateTime, BigDecimal amount) {
    return new CreateTransactionInput(
        accountId, organizationId, dateTime, UUID.randomUUID().toString(), amount);
  }
}
