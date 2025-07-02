package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.PaymentMapper;
import dev.thiagooliveira.bankhub.infra.service.PaymentService;
import dev.thiagooliveira.bankhub.spec.http.controllers.PaymentsApi;
import dev.thiagooliveira.bankhub.spec.http.dto.PostPaymentRequestBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostPaymentResponseBody;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController implements PaymentsApi {
  private final AppProps appProps;
  private final PaymentService paymentService;
  private final PaymentMapper paymentMapper;

  public PaymentController(
      AppProps appProps, PaymentService paymentService, PaymentMapper paymentMapper) {
    this.appProps = appProps;
    this.paymentService = paymentService;
    this.paymentMapper = paymentMapper;
  }

  @Override
  public ResponseEntity<PostPaymentResponseBody> pay(
      PostPaymentRequestBody postPaymentRequestBody) {
    var payment =
        this.paymentService.create(
            this.paymentMapper.map(this.appProps.getOrganizationId(), postPaymentRequestBody));
    return ResponseEntity.created(URI.create(String.format("/api/payments/%s", payment.id())))
        .body(this.paymentMapper.map(payment));
  }
}
