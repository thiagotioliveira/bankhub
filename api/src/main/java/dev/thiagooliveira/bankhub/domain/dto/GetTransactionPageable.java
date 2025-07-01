package dev.thiagooliveira.bankhub.domain.dto;

import java.util.List;
import java.util.UUID;

public record GetTransactionPageable(
    List<UUID> accountIds, UUID organizationId, Pageable pageable) {

  public GetTransactionPageable(List<UUID> accountIds, UUID organizationId, int page, int size) {
    this(accountIds, organizationId, new Pageable(page, size));
  }

  public GetTransactionPageable enrichWith(List<UUID> accountIds) {
    return new GetTransactionPageable(accountIds, organizationId, pageable);
  }
}
