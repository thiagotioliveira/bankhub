package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryPort {

  Category create(CreateCategoryInput input);

  Optional<Category> findByTypeAndOrganizationIdIsNull(CategoryType type);

  Optional<Category> findByIdAndOrganizationIdOrOrganizationIdIsNull(UUID id, UUID organizationId);

  List<Category> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId);

  boolean existsByNameIgnoreCaseAndOrganizationIdAndType(
      String name, UUID organizationId, CategoryType type);
}
