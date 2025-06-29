package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateCategory;
import dev.thiagooliveira.bankhub.application.usecase.GetCategory;
import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CreateCategory createCategory;
  private final GetCategory getCategory;

  public CategoryService(CreateCategory createCategory, GetCategory getCategory) {
    this.createCategory = createCategory;
    this.getCategory = getCategory;
  }

  public Category create(CreateCategoryInput input) {
    return createCategory.create(input);
  }

  public Optional<Category> findByType(CategoryType type) {
    return getCategory.findByType(type);
  }

  public Optional<Category> findById(UUID id, UUID organisationId) {
    return getCategory.findById(id, organisationId);
  }

  public List<Category> findAll(UUID organisationId) {
    return getCategory.findAll(organisationId);
  }
}
