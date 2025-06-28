package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import java.util.UUID;

public interface AccountPort {

  Account create(CreateAccountInput input);

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
