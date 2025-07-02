package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.BankMapper;
import dev.thiagooliveira.bankhub.infra.service.BankService;
import dev.thiagooliveira.bankhub.spec.http.controllers.BanksApi;
import dev.thiagooliveira.bankhub.spec.http.dto.GetBankResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostBankRequestBody;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BanksController implements BanksApi {
  private final AppProps appProps;
  private final BankService bankService;
  private final BankMapper bankMapper;

  public BanksController(AppProps appProps, BankService bankService, BankMapper bankMapper) {
    this.appProps = appProps;
    this.bankService = bankService;
    this.bankMapper = bankMapper;
  }

  @Override
  public ResponseEntity<GetBankResponseBody> createBank(PostBankRequestBody postBankRequestBody) {
    var bank =
        this.bankMapper.map(
            this.bankService.create(
                this.bankMapper.map(this.appProps.getOrganizationId(), postBankRequestBody)));
    return ResponseEntity.created(URI.create(String.format("/api/banks/%s", bank.getId())))
        .body(bank);
  }

  @Override
  public ResponseEntity<GetBankResponseBody> getBankById(UUID id) {
    return ResponseEntity.ok(
        this.bankService
            .findById(id, this.appProps.getOrganizationId())
            .map(this.bankMapper::map)
            .orElseThrow(() -> BusinessLogicException.notFound("bank not found")));
  }

  @Override
  public ResponseEntity<List<GetBankResponseBody>> listBanks() {
    return ResponseEntity.ok(
        this.bankService.findByOrganizationId(this.appProps.getOrganizationId()).stream()
            .map(this.bankMapper::map)
            .toList());
  }
}
