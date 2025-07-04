package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateUserInput;
import dev.thiagooliveira.bankhub.domain.model.User;
import dev.thiagooliveira.bankhub.domain.port.UserPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.UserEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserAdapter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User create(CreateUserInput input) {
    return this.userRepository.save(UserEntity.from(input, passwordEncoder)).toDomain();
  }
}
