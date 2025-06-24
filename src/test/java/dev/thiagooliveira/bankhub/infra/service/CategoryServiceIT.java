package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.createCategoryInput;
import static dev.thiagooliveira.bankhub.util.TestUtil.createOrganizationInput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.thiagooliveira.bankhub.TestcontainersConfiguration;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CategoryServiceIT {

  @Autowired private CategoryService categoryService;

  @Autowired private OrganizationService organizationService;

  private UUID organizationId;

  @BeforeEach
  void setUp() {
    this.organizationId = this.organizationService.create(createOrganizationInput()).id();
  }

  @Test
  void create() {
    Category category =
        this.categoryService.create(
            createCategoryInput(Optional.of(this.organizationId), CategoryType.CREDIT));
    assertNotNull(category);
    assertNotNull(category.name());
    assertEquals(this.organizationId, category.organizationId().get());
    assertEquals(CategoryType.CREDIT, category.type());
  }
}
