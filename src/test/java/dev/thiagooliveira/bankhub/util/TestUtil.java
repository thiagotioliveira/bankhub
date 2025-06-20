package dev.thiagooliveira.bankhub.util;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.util.UUID;

public class TestUtil {

  public static CreateOrganizationInput createOrganizationInput() {
    return new CreateOrganizationInput("Jack Bauer", "j.bauer@bankhub.app");
  }

  public static CreateCategoryInput createCategoryInput(
      UUID organizationId, CategoryType categoryType) {
    return new CreateCategoryInput(organizationId, "Category " + categoryType.name(), categoryType);
  }

  public static CreateBankInput createBankInput(UUID organizationId) {
    return new CreateBankInput(organizationId, "Bank " + UUID.randomUUID());
  }

  public static CreateAccountInput createAccountInput(
      UUID organizationId, UUID bankId, BigDecimal balance, Currency currency) {
    return new CreateAccountInput(
        "Account " + UUID.randomUUID(), bankId, organizationId, balance, currency);
  }
}
