package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateUserInput;
import dev.thiagooliveira.bankhub.domain.model.User;

public interface UserPort {
  User create(CreateUserInput input);
}
