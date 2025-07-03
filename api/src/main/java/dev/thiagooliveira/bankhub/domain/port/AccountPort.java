package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.MonthlyAccountSummary;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountPort {

  List<Account> findByOrganizationId(UUID organizationId);

  Optional<Account> findByIdAndOrganizationId(UUID id, UUID organizationId);

  Optional<AccountEnriched> findByIdAndOrganizationIdEnriched(UUID id, UUID organizationId);

  Account create(CreateAccountInput input);

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);

  void update(UUID id, BigDecimal newBalance);

  void createMonthlyAccountSummary(MonthlyAccountSummary summary);
}
