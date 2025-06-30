package dev.thiagooliveira.bankhub.infra.http;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.mapper.PayableReceivableMapper;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import dev.thiagooliveira.bankhub.spec.http.controllers.PayableReceivableApi;
import dev.thiagooliveira.bankhub.spec.http.dto.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayableReceivableController implements PayableReceivableApi {

  private final AppProps appProps;
  private final PayableReceivableService payableReceivableService;
  private final PayableReceivableMapper payableReceivableMapper;

  public PayableReceivableController(
      AppProps appProps,
      PayableReceivableService payableReceivableService,
      PayableReceivableMapper payableReceivableMapper) {
    this.appProps = appProps;
    this.payableReceivableService = payableReceivableService;
    this.payableReceivableMapper = payableReceivableMapper;
  }

  @Override
  public ResponseEntity<List<GetPayableReceivableResponseBody>> getPayableReceivable() {
    return ResponseEntity.ok(
        this.payableReceivableService
            .getPayableReceivables(this.appProps.getOrganizationId())
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
  public ResponseEntity<PostPayableReceivablePaidResponseBody> payById(
      UUID id, PostPayableReceivablePaidRequestBody postPayableReceivablePaidRequestBody) {
    var paid =
        this.payableReceivableService.pay(
            this.payableReceivableMapper.map(
                id, this.appProps.getOrganizationId(), postPayableReceivablePaidRequestBody));
    return ResponseEntity.created(
            URI.create(String.format("/api/payable-receivable/%s", paid.id())))
        .body(this.payableReceivableMapper.mapToPostPayableReceivablePaidResponseBody(paid));
  }

  @Override
  public ResponseEntity<List<PostPayableReceivableResponseBody>> postPayable(
      PostPayableReceivableRequestBody postPayableReceivableRequestBody) {
    return new ResponseEntity<>(
        this.payableReceivableService
            .create(
                this.payableReceivableMapper.map(
                    PayableReceivableType.PAYABLE,
                    this.appProps.getOrganizationId(),
                    postPayableReceivableRequestBody))
            .stream()
            .map(this.payableReceivableMapper::mapToPostPayableReceivableResponseBody)
            .toList(),
        HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<PostPayableReceivableResponseBody>> postReceivable(
      PostPayableReceivableRequestBody postPayableReceivableRequestBody) {
    return new ResponseEntity<>(
        this.payableReceivableService
            .create(
                this.payableReceivableMapper.map(
                    PayableReceivableType.RECEIVABLE,
                    this.appProps.getOrganizationId(),
                    postPayableReceivableRequestBody))
            .stream()
            .map(this.payableReceivableMapper::mapToPostPayableReceivableResponseBody)
            .toList(),
        HttpStatus.CREATED);
  }
}
