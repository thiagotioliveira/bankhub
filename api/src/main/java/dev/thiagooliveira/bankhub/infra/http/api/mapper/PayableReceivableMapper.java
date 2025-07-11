package dev.thiagooliveira.bankhub.infra.http.api.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.UpdatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import dev.thiagooliveira.bankhub.spec.http.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper
public interface PayableReceivableMapper {
  UpdatePayableReceivableInput map(
      UUID payableReceivableId,
      UUID organizationId,
      PatchPayableReceivableRequestBody patchPayableReceivableRequestBody);

  CreatePayableReceivableInput map(
      PayableReceivableType type,
      UUID organizationId,
      PostPayableReceivableRequestBody postPayableReceivableRequestBody);

  GetPayableReceivableResponseBody map(PayableReceivableEnriched payableReceivableEnriched);

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

  default Optional<Integer> mapIntegerToOptional(JsonNullable<Integer> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Integer mapInteger2(Optional<Integer> value) {
    return value.orElse(null);
  }

  default UUID map(Optional<UUID> value) {
    return value.orElse(null);
  }

  default Optional<OffsetDateTime> map(JsonNullable<OffsetDateTime> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Optional<BigDecimal> mapBigDecimal(JsonNullable<BigDecimal> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Optional<LocalDate> mapLocalDate(JsonNullable<LocalDate> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Optional<Frequency> mapFrequency(
      JsonNullable<PostPayableReceivableRequestBody.FrequencyEnum> value) {
    return value.isPresent()
        ? Optional.of(Frequency.valueOf(value.get().name()))
        : Optional.empty();
  }
}
