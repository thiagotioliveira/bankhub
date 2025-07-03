package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.infra.config.support.AppProperties;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.CategoryMapper;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import dev.thiagooliveira.bankhub.spec.http.controllers.CategoriesApi;
import dev.thiagooliveira.bankhub.spec.http.dto.GetCategoryResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostCategoryRequestBody;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController implements CategoriesApi {

  private final AppProperties appProps;
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  public CategoriesController(
      AppProperties appProps, CategoryService categoryService, CategoryMapper categoryMapper) {
    this.appProps = appProps;
    this.categoryService = categoryService;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public ResponseEntity<GetCategoryResponseBody> createCategory(
      PostCategoryRequestBody postCategoryRequestBody) {
    var category =
        this.categoryService.create(
            this.categoryMapper.map(appProps.getOrganizationId(), postCategoryRequestBody));
    return ResponseEntity.created(URI.create(String.format("/api/categories/%s", category.id())))
        .body(this.categoryMapper.map(category));
  }

  @Override
  public ResponseEntity<GetCategoryResponseBody> getCategoryById(UUID id) {
    return ResponseEntity.ok(
        this.categoryService
            .findById(id, appProps.getOrganizationId())
            .map(this.categoryMapper::map)
            .orElseThrow(() -> BusinessLogicException.notFound("category not found")));
  }

  @Override
  public ResponseEntity<List<GetCategoryResponseBody>> listCategories() {

    return ResponseEntity.ok(
        this.categoryService.findAll(appProps.getOrganizationId()).stream()
            .map(this.categoryMapper::map)
            .toList());
  }
}
