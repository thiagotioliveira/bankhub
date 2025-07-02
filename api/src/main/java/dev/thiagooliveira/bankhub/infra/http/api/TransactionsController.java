package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.TransactionMapper;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import dev.thiagooliveira.bankhub.spec.http.controllers.TransactionsApi;
import dev.thiagooliveira.bankhub.spec.http.dto.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController implements TransactionsApi {

  private final AppProps appProps;
  private final TransactionService transactionService;
  private final TransactionMapper transactionMapper;

  public TransactionsController(
      AppProps appProps,
      TransactionService transactionService,
      TransactionMapper transactionMapper,
      CategoryService categoryService) {
    this.appProps = appProps;
    this.transactionService = transactionService;
    this.transactionMapper = transactionMapper;
  }

  @Override
  public ResponseEntity<PostDepositResponseBody> createDeposit(
      PostDepositRequestBody postDepositRequestBody) {
    var transaction =
        this.transactionService.createDeposit(
            this.transactionMapper.map(appProps.getOrganizationId(), postDepositRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/transactions/%s", transaction.id())))
        .body(this.transactionMapper.mapToPostDepositResponseBody(transaction));
  }

  @Override
  public ResponseEntity<PostWithdrawalResponseBody> createWithdrawal(
      PostWithdrawalRequestBody postWithdrawalRequestBody) {
    var transaction =
        this.transactionService.createWithdrawal(
            this.transactionMapper.map(appProps.getOrganizationId(), postWithdrawalRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/transactions/%s", transaction.id())))
        .body(this.transactionMapper.mapToPostWithdrawalResponseBody(transaction));
  }

  @Override
  public ResponseEntity<GetTransactionsResponseBody> getByFiltersOrderByDateTime(
      List<UUID> accountIds, Integer page, Integer size) {
    return ResponseEntity.ok(
        this.transactionMapper.map(
            this.transactionService.findEnrichedByFiltersOrderByDateTime(
                new GetTransactionPageable(
                    accountIds != null ? accountIds : List.of(),
                    this.appProps.getOrganizationId(),
                    page != null ? page : 0,
                    size != null ? size : 20))));
  }
}
