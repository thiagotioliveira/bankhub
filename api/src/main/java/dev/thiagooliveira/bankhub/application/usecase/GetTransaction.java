package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.domain.model.*;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GetTransaction {
  private final TransactionPort transactionPort;
  private final GetAccount getAccount;

  public GetTransaction(
      TransactionPort transactionPort,
      GetAccount getAccount,
      GetPayableReceivable getPayableReceivable) {
    this.transactionPort = transactionPort;
    this.getAccount = getAccount;
  }

  public Page<TransactionEnriched> findEnrichedByFiltersOrderByDateTime(
      GetTransactionPageable pageable) {
    if (pageable.accountIds().isEmpty()) {
      var accountIds =
          this.getAccount.findByOrganizationId(pageable.organizationId()).stream()
              .map(Account::id)
              .toList();
      return this.transactionPort.findEnrichedByFiltersOrderByDateTime(
          pageable.enrichWith(accountIds));
    } else {
      return this.transactionPort.findEnrichedByFiltersOrderByDateTime(pageable);
    }
  }

  public List<TransactionEnriched> getByOrganizationId(
      UUID organizationId, LocalDate from, LocalDate to) {
    return this.transactionPort.getByOrganizationId(organizationId, from, to);
  }
}
