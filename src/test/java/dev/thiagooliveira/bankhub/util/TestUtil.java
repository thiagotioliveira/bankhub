package dev.thiagooliveira.bankhub.util;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.UUID;

public class TestUtil {

  public static CreateOrganizationInput createOrganizationInput() {
    return new CreateOrganizationInput("Jack Bauer", "j.bauer@bankhub.app");
  }

  public static CreateCategoryInput createCategoryInput(
      UUID organizationId, CategoryType categoryType) {
    return new CreateCategoryInput(organizationId, "Category " + categoryType.name(), categoryType);
  }
}
