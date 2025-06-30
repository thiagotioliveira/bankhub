package dev.thiagooliveira.bankhub.infra.http;

import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.mapper.TransactionMapper;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import dev.thiagooliveira.bankhub.spec.http.controllers.TransactionsApi;
import dev.thiagooliveira.bankhub.spec.http.dto.*;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController implements TransactionsApi {

  private final AppProps appProps;
  private final TransactionService transactionService;
  private final TransactionMapper transactionMapper;
  private final CategoryService categoryService;

  public TransactionsController(
      AppProps appProps,
      TransactionService transactionService,
      TransactionMapper transactionMapper,
      CategoryService categoryService) {
    this.appProps = appProps;
    this.transactionService = transactionService;
    this.transactionMapper = transactionMapper;
    this.categoryService = categoryService;
  }

  @Override
  public ResponseEntity<PostDepositResponseBody> createDeposit(
      PostDepositRequestBody postDepositRequestBody) {
    categoryService
        .findById(postDepositRequestBody.getCategoryId(), this.appProps.getOrganizationId())
        .ifPresent(
            c -> {
              if (!c.isCredit()) {
                throw BusinessLogicException.badRequest("category needs to be credit");
              }
            });
    var transaction =
        this.transactionService.create(
            this.transactionMapper.map(appProps.getOrganizationId(), postDepositRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/transactions/%s", transaction.id())))
        .body(this.transactionMapper.mapToPostDepositResponseBody(transaction));
  }

  @Override
  public ResponseEntity<PostWithdrawalResponseBody> createWithdrawal(
      PostWithdrawalRequestBody postWithdrawalRequestBody) {
    categoryService
        .findById(postWithdrawalRequestBody.getCategoryId(), this.appProps.getOrganizationId())
        .ifPresent(
            c -> {
              if (!c.isDebit()) {
                throw BusinessLogicException.badRequest("category needs to be debit");
              }
            });
    var transaction =
        this.transactionService.create(
            this.transactionMapper.map(appProps.getOrganizationId(), postWithdrawalRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/transactions/%s", transaction.id())))
        .body(this.transactionMapper.mapToPostWithdrawalResponseBody(transaction));
  }

  @Override
  public ResponseEntity<GetTransactionsResponseBody> getByFiltersOrderByDateTime(
      List<UUID> accountIds, OffsetDateTime start, OffsetDateTime end, Integer page, Integer size) {
    return ResponseEntity.ok(
        this.transactionMapper.map(
            this.transactionService.findEnrichedByFiltersOrderByDateTime(
                new GetTransactionPageable(
                    accountIds != null ? accountIds : List.of(),
                    this.appProps.getOrganizationId(),
                    start != null ? start : OffsetDateTime.now().minusDays(1),
                    end != null ? end : OffsetDateTime.now(),
                    page != null ? page : 0,
                    size != null ? size : 10))));
  }
}
