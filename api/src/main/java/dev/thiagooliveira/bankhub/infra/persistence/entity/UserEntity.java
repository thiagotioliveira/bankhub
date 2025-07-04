package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreateUserInput;
import dev.thiagooliveira.bankhub.domain.model.User;
import jakarta.persistence.*;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private UUID organizationId;

  public UserEntity() {}

  public UserEntity(
      UUID id, String email, String name, String password, boolean enabled, UUID organizationId) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.password = password;
    this.enabled = enabled;
    this.organizationId = organizationId;
  }

  public static UserEntity from(CreateUserInput input, PasswordEncoder passwordEncoder) {
    UserEntity userEntity = new UserEntity();
    userEntity.id = UUID.randomUUID();
    userEntity.email = input.email();
    userEntity.name = input.name();
    userEntity.password = passwordEncoder.encode(input.password());
    userEntity.enabled = true;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
