package dev.thiagooliveira.bankhub.domain.dto;

import java.util.UUID;

public record GetTransactionPageable(UUID accountId, Pageable pageable) {}
