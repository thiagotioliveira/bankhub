package dev.thiagooliveira.bankhub.infra.http;

import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.mapper.TransactionMapper;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import dev.thiagooliveira.bankhub.spec.http.controllers.TransactionsApi;
import dev.thiagooliveira.bankhub.spec.http.dto.PostDepositRequestBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostDepositResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostWithdrawalRequestBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostWithdrawalResponseBody;
import java.net.URI;
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
      TransactionMapper transactionMapper) {
    this.appProps = appProps;
    this.transactionService = transactionService;
    this.transactionMapper = transactionMapper;
  }

  @Override
  public ResponseEntity<PostDepositResponseBody> createDeposit(
      PostDepositRequestBody postDepositRequestBody) {
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
    var transaction =
        this.transactionService.create(
            this.transactionMapper.map(appProps.getOrganizationId(), postWithdrawalRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/transactions/%s", transaction.id())))
        .body(this.transactionMapper.mapToPostWithdrawalResponseBody(transaction));
  }
}
