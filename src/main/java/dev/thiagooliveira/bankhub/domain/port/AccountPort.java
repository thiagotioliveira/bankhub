package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;

public interface AccountPort {

  Account create(CreateAccountInput input);
}
