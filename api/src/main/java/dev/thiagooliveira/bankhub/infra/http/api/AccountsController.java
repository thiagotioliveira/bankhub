package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.AccountMapper;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.spec.http.controllers.AccountsApi;
import dev.thiagooliveira.bankhub.spec.http.dto.GetAccountResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.GetAccountsResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostAccountRequestBody;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController implements AccountsApi {

  private final AppProps appProps;
  private final AccountService accountService;
  private final AccountMapper accountMapper;

  public AccountsController(
      AppProps appProps, AccountService accountService, AccountMapper accountMapper) {
    this.appProps = appProps;
    this.accountService = accountService;
    this.accountMapper = accountMapper;
  }

  @Override
  public ResponseEntity<GetAccountsResponseBody> createAccount(
      PostAccountRequestBody postAccountRequestBody) {
    var account =
        this.accountMapper.map(
            this.accountService.create(
                this.accountMapper.map(appProps.getOrganizationId(), postAccountRequestBody),
                postAccountRequestBody.getInitialBalance()));
    return ResponseEntity.created(URI.create(String.format("/api/accounts/%s", account.getId())))
        .body(account);
  }

  @Override
  public ResponseEntity<GetAccountResponseBody> getAccountById(UUID id) {
    return ResponseEntity.ok(
        this.accountService
            .findByIdAndOrganizationIdEnriched(id, this.appProps.getOrganizationId())
            .map(this.accountMapper::map)
            .orElseThrow(() -> BusinessLogicException.notFound("account not found")));
  }

  @Override
  public ResponseEntity<List<GetAccountsResponseBody>> listAccounts() {
    return ResponseEntity.ok(
        this.accountService.findByOrganizationId(this.appProps.getOrganizationId()).stream()
            .map(this.accountMapper::map)
            .toList());
  }
}
