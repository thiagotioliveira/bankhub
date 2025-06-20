package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "banks")
public class BankEntity {
  @Id private UUID id;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(nullable = false)
  private String name;

  public BankEntity() {}

  public BankEntity(UUID id, UUID organizationId, String name) {
    this.id = id;
    this.organizationId = organizationId;
    this.name = name;
  }

  public static BankEntity from(CreateBankInput input) {
    BankEntity entity = new BankEntity();
    entity.id = UUID.randomUUID();
    entity.name = input.name();
    entity.organizationId = input.organizationId();
    return entity;
  }

  public Bank toDomain() {
    return new Bank(id, organizationId, name);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
