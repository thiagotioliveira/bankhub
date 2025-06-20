package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateUserInput;
import dev.thiagooliveira.bankhub.domain.model.User;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private UUID organizationId;

  public UserEntity() {}

  public UserEntity(UUID id, String email, String name, UUID organizationId) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.organizationId = organizationId;
  }

  public static UserEntity from(CreateUserInput input) {
    UserEntity userEntity = new UserEntity();
    userEntity.id = UUID.randomUUID();
    userEntity.email = input.email();
    userEntity.name = input.name();
    userEntity.organizationId = input.organizationId();
    return userEntity;
  }

  public User toDomain() {
    return new User(id, email, name, organizationId);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganization(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
