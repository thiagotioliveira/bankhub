package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.Optional;
import java.util.UUID;

public interface CategoryPort {

  Category create(CreateCategoryInput input);

  Optional<Category> findByType(CategoryType type);

  Optional<Category> findById(UUID id);
}
