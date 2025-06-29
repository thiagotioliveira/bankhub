package dev.thiagooliveira.bankhub.domain.dto.projection;

import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.UUID;

public record CategoryEnriched(UUID id, String name, CategoryType type) {
  public CategoryEnriched(UUID id, String name, String type) {
    this(id, name, CategoryType.valueOf(type));
  }
}
