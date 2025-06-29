package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.infra.persistence.entity.CategoryEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
  Optional<CategoryEntity> findByTypeAndOrganizationIdIsNull(CategoryType type);

  @Modifying
  @Transactional
  @Query("DELETE FROM CategoryEntity c WHERE c.organizationId IS NOT NULL")
  void deleteAllWhereOrganizationIdIsNotNull();

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
