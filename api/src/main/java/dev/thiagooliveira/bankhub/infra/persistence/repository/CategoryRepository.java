package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.infra.persistence.entity.CategoryEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
  @Query(
      """
  SELECT c FROM CategoryEntity c
  WHERE c.id = :id AND (c.organizationId = :organizationId OR c.organizationId IS NULL)
""")
  Optional<CategoryEntity> findByIdAndOrganizationIdOrOrganizationIdIsNull(
      @Param("id") UUID id, @Param("organizationId") UUID organizationId);

  Optional<CategoryEntity> findByTypeAndOrganizationIdIsNull(CategoryType type);

  List<CategoryEntity> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId);

  @Modifying
  @Transactional
  @Query("DELETE FROM CategoryEntity c WHERE c.organizationId IS NOT NULL")
  void deleteAllWhereOrganizationIdIsNotNull();

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
