package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.port.CategoryPort;
import java.util.Optional;

public class GetCategory {

  private final CategoryPort categoryPort;

  public GetCategory(CategoryPort categoryPort) {
    this.categoryPort = categoryPort;
  }

  public Optional<Category> findByType(CategoryType type) {
    return this.categoryPort.findByType(type);
  }
}
