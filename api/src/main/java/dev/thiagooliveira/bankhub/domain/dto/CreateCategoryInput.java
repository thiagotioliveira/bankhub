package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.Optional;
import java.util.UUID;

public record CreateCategoryInput(Optional<UUID> organizationId, String name, CategoryType type) {}
