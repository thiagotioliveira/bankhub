package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.port.CategoryPort;

public class CreateCategory {

  private final CategoryPort categoryPort;

  public CreateCategory(CategoryPort categoryPort) {
    this.categoryPort = categoryPort;
  }

  public Category create(CreateCategoryInput input) {
    if (input.organizationId().isPresent()
        && this.categoryPort.existsByNameIgnoreCaseAndOrganizationIdAndType(
            input.name(), input.organizationId().get(), input.type())) {
      throw BusinessLogicException.badRequest("the name already exists");
    }
    return this.categoryPort.create(input);
  }
}
