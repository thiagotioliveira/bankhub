package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import dev.thiagooliveira.bankhub.infra.config.support.AppProperties;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.PayableReceivableMapper;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import dev.thiagooliveira.bankhub.spec.http.controllers.PayableReceivableApi;
import dev.thiagooliveira.bankhub.spec.http.dto.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayableReceivableController implements PayableReceivableApi {

  private final AppProperties appProps;
  private final PayableReceivableService payableReceivableService;
  private final PayableReceivableMapper payableReceivableMapper;

  public PayableReceivableController(
      AppProperties appProps,
      PayableReceivableService payableReceivableService,
      PayableReceivableMapper payableReceivableMapper) {
    this.appProps = appProps;
    this.payableReceivableService = payableReceivableService;
    this.payableReceivableMapper = payableReceivableMapper;
  }

  @Override
  public ResponseEntity<List<GetPayableReceivableResponseBody>> getPayableReceivable(
      LocalDate start, LocalDate end) {
    return ResponseEntity.ok(
        this.payableReceivableService
            .getPayableReceivables(this.appProps.getOrganizationId(), start, end)
            .stream()
            .map(this.payableReceivableMapper::map)
            .toList());
  }

  @Override
  public ResponseEntity<GetPayableReceivableResponseBody> getPayableReceivableById(UUID id) {
    return ResponseEntity.ok(
        this.payableReceivableMapper.map(
            this.payableReceivableService
                .getPayableReceivable(id, this.appProps.getOrganizationId())
                .orElseThrow(
                    () -> BusinessLogicException.notFound("payable/receivable not found"))));
  }

  @Override
  public ResponseEntity<PostPayableReceivableResponseBody> postPayable(
      PostPayableReceivableRequestBody postPayableReceivableRequestBody) {
    var created =
        this.payableReceivableService.create(
            this.payableReceivableMapper.map(
                PayableReceivableType.PAYABLE,
                this.appProps.getOrganizationId(),
                postPayableReceivableRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/payable-receivable/%s", created.id())))
        .body(this.payableReceivableMapper.mapToPostPayableReceivableResponseBody(created));
  }

  @Override
  public ResponseEntity<PostPayableReceivableResponseBody> postReceivable(
      PostPayableReceivableRequestBody postPayableReceivableRequestBody) {
    var created =
        this.payableReceivableService.create(
            this.payableReceivableMapper.map(
                PayableReceivableType.RECEIVABLE,
                this.appProps.getOrganizationId(),
                postPayableReceivableRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/payable-receivable/%s", created.id())))
        .body(this.payableReceivableMapper.mapToPostPayableReceivableResponseBody(created));
  }

  @Override
  public ResponseEntity<Void> updatePayableReceivableById(
      UUID id, PatchPayableReceivableRequestBody patchPayableReceivableRequestBody) {
    this.payableReceivableService.update(
        this.payableReceivableMapper.map(
            id, this.appProps.getOrganizationId(), patchPayableReceivableRequestBody));
    return ResponseEntity.noContent().build();
  }
}
