package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity {
  @Id private UUID id;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CategoryType type;

  public CategoryEntity() {}

  public CategoryEntity(UUID id, UUID organizationId, String name, CategoryType type) {
    this.id = id;
    this.organizationId = organizationId;
    this.name = name;
    this.type = type;
  }

  public static CategoryEntity from(CreateCategoryInput input) {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.id = UUID.randomUUID();
    categoryEntity.organizationId = input.organizationId();
    categoryEntity.name = input.name();
    categoryEntity.type = input.type();
    return categoryEntity;
  }

  public Category toDomain() {
    return new Category(id, organizationId, name, type);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CategoryType getType() {
    return type;
  }

  public void setType(CategoryType type) {
    this.type = type;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
