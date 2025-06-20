package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.UserRegistrationInput;
import dev.thiagooliveira.bankhub.domain.model.User;

public interface UserPort {
  User create(UserRegistrationInput input);
}
