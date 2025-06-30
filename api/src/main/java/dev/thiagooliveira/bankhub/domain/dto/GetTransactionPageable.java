package dev.thiagooliveira.bankhub.domain.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record GetTransactionPageable(
    List<UUID> accountIds,
    UUID organizationId,
    OffsetDateTime startDateTime,
    OffsetDateTime endDateTime,
    Pageable pageable) {

  public GetTransactionPageable(
      List<UUID> accountIds,
      UUID organizationId,
      OffsetDateTime startDateTime,
      OffsetDateTime endDateTime,
      int page,
      int size) {
    this(accountIds, organizationId, startDateTime, endDateTime, new Pageable(page, size));
  }

  public GetTransactionPageable enrichWith(List<UUID> accountIds) {
    return new GetTransactionPageable(
        accountIds, organizationId, startDateTime, endDateTime, pageable);
  }
}
