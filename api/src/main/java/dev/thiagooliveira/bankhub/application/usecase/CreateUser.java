package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateUserInput;
import dev.thiagooliveira.bankhub.domain.model.User;
import dev.thiagooliveira.bankhub.domain.port.UserPort;

public class CreateUser {

  private final UserPort userPort;

  public CreateUser(UserPort userPort) {
    this.userPort = userPort;
  }

  public User create(CreateUserInput input) {
    return this.userPort.create(input);
  }
}
