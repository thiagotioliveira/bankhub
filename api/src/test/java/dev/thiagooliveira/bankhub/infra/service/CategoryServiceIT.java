package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.createCategoryInput;
import static dev.thiagooliveira.bankhub.util.TestUtil.createOrganizationInput;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.IntegrationTest;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryServiceIT extends IntegrationTest {

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

  @Test
  void createWithSameName() {
    this.categoryService.create(
        createCategoryInput(Optional.of(this.organizationId), CategoryType.CREDIT));
    assertThrows(
        BusinessLogicException.class,
        () ->
            this.categoryService.create(
                createCategoryInput(Optional.of(this.organizationId), CategoryType.CREDIT)));
  }

  @Test
  void createWithSameNameTypeDifferent() {
    this.categoryService.create(
        createCategoryInput(Optional.of(this.organizationId), CategoryType.CREDIT));
    assertNotNull(
        this.categoryService.create(
            createCategoryInput(Optional.of(this.organizationId), CategoryType.DEBIT)));
  }
}
