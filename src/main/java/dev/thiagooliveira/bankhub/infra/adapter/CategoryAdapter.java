package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.port.CategoryPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.CategoryEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.CategoryRepository;
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
}
