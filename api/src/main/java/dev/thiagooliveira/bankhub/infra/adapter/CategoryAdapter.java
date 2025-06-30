package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.port.CategoryPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.CategoryEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CategoryAdapter implements CategoryPort {

  private final CategoryRepository categoryRepository;

  public CategoryAdapter(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category create(CreateCategoryInput input) {
    return this.categoryRepository.save(CategoryEntity.from(input)).toDomain();
  }

  @Override
  public Optional<Category> findByTypeAndOrganizationIdIsNull(CategoryType type) {
    return this.categoryRepository
        .findByTypeAndOrganizationIdIsNull(type)
        .map(CategoryEntity::toDomain);
  }

  @Override
  public Optional<Category> findByIdAndOrganizationIdOrOrganizationIdIsNull(
      UUID id, UUID organizationId) {
    return this.categoryRepository.findById(id).map(CategoryEntity::toDomain);
  }

  @Override
  public List<Category> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId) {
    return this.categoryRepository
        .findByOrganizationIdOrOrganizationIdIsNull(organizationId)
        .stream()
        .map(CategoryEntity::toDomain)
        .toList();
  }

  @Override
  public boolean existsByNameIgnoreCaseAndOrganizationIdAndType(
      String name, UUID organizationId, CategoryType type) {
    return this.categoryRepository.existsByNameIgnoreCaseAndOrganizationIdAndType(
        name, organizationId, type);
  }
}
