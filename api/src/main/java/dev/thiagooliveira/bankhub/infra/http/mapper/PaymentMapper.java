package dev.thiagooliveira.bankhub.infra.http.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import dev.thiagooliveira.bankhub.spec.http.dto.PostPaymentRequestBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostPaymentResponseBody;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper
public interface PaymentMapper {
  CreatePaymentInput map(UUID organizationId, PostPaymentRequestBody postPaymentRequestBody);

  PostPaymentResponseBody map(Payment payment);

  default Optional<OffsetDateTime> mapOffsetDateTime(JsonNullable<OffsetDateTime> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Optional<String> mapString(JsonNullable<String> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Optional<BigDecimal> mapBigDecimal(JsonNullable<BigDecimal> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }
}
