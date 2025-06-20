package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateCategory;
import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CreateCategory createCategory;

  public CategoryService(CreateCategory createCategory) {
    this.createCategory = createCategory;
  }

  public Category create(CreateCategoryInput input) {
    return createCategory.create(input);
  }
}
