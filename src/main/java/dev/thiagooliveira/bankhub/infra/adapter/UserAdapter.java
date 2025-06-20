package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.UserRegistrationInput;
import dev.thiagooliveira.bankhub.domain.model.User;
import dev.thiagooliveira.bankhub.domain.port.UserPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.UserEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

  private final UserRepository userRepository;

  public UserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User create(UserRegistrationInput input) {
    return this.userRepository.save(UserEntity.from(input)).toDomain();
  }
}
