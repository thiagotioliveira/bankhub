package dev.thiagooliveira.bankhub.infra.security;

import dev.thiagooliveira.bankhub.infra.persistence.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

  private final UserEntity user;

  public UserPrincipal(UserEntity user) {
    this.user = user;
  }

  public UUID getOrganizationId() {
    return user.getOrganizationId();
  }

  public UUID getId() {
    return user.getId();
  }

  public String getName() {
    return user.getName();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public UserEntity getUser() {
    return user;
  }
}
