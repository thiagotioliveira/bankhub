package dev.thiagooliveira.bankhub.infra.http.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.http.dto.GetCategoryResponseBody;
import dev.thiagooliveira.bankhub.http.dto.PostCategoryRequestBody;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

  GetCategoryResponseBody map(Category category);

  CreateCategoryInput map(UUID organizationId, PostCategoryRequestBody postCategoryRequestBody);

  default Optional<UUID> map(UUID value) {
    return Optional.ofNullable(value);
  }
}
