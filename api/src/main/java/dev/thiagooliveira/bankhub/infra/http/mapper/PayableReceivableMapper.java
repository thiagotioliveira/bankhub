package dev.thiagooliveira.bankhub.infra.http.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import dev.thiagooliveira.bankhub.spec.http.dto.GetPayableReceivableResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostPayableReceivableRequestBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostPayableReceivableResponseBody;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper
public interface PayableReceivableMapper {

  CreatePayableReceivableInput map(
      PayableReceivableType type,
      UUID organizationId,
      PostPayableReceivableRequestBody postPayableReceivableRequestBody);

  GetPayableReceivableResponseBody map(PayableReceivable payableReceivable);

  PostPayableReceivableResponseBody mapToPostPayableReceivableResponseBody(
      PayableReceivable payableReceivable);

  default Optional<Frequency> map(dev.thiagooliveira.bankhub.spec.http.dto.Frequency value) {
    return value != null ? Optional.of(Frequency.valueOf(value.name())) : Optional.empty();
  }

  default JsonNullable<Integer> map(Integer value) {
    return value != null ? JsonNullable.of(value) : JsonNullable.undefined();
  }

  default dev.thiagooliveira.bankhub.spec.http.dto.Frequency mapFrequency(
      Optional<Frequency> value) {
    return value
        .map(
            frequency ->
                dev.thiagooliveira.bankhub.spec.http.dto.Frequency.valueOf(frequency.name()))
        .orElse(null);
  }

  default Integer mapInteger(JsonNullable<Integer> value) {
    return value.orElse(null);
  }

  default Optional<Integer> mapIntegerToOptional(JsonNullable<Integer> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Integer mapInteger2(Optional<Integer> value) {
    return value.orElse(null);
  }

  default UUID map(Optional<UUID> value) {
    return value.orElse(null);
  }
}
