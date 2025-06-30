package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.port.CategoryPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetCategory {

  private final CategoryPort categoryPort;

  public GetCategory(CategoryPort categoryPort) {
    this.categoryPort = categoryPort;
  }

  public Optional<Category> findByType(CategoryType type) {
    return this.categoryPort.findByTypeAndOrganizationIdIsNull(type);
  }

  public Optional<Category> findById(UUID id, UUID organisationId) {
    return this.categoryPort.findByIdAndOrganizationIdOrOrganizationIdIsNull(id, organisationId);
  }

  public List<Category> findAll(UUID organisationId) {
    return this.categoryPort.findByOrganizationIdOrOrganizationIdIsNull(organisationId);
  }
}
