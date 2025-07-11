package dev.thiagooliveira.bankhub.infra.http.api.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.spec.http.dto.*;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

  @Mapping(
      target = "dateTime",
      expression =
          "java(postDepositRequestBody.getDateTime().isPresent() ? postDepositRequestBody.getDateTime().get() : OffsetDateTime.now())")
  @Mapping(
      target = "description",
      expression =
          "java(postDepositRequestBody.getDescription().isPresent() ? postDepositRequestBody.getDescription().get() : \"deposit\")")
  CreateTransactionInput map(UUID organizationId, PostDepositRequestBody postDepositRequestBody);

  PostDepositResponseBody mapToPostDepositResponseBody(Transaction transaction);

  @Mapping(
      target = "dateTime",
      expression =
          "java(postWithdrawalRequestBody.getDateTime().isPresent() ? postWithdrawalRequestBody.getDateTime().get() : OffsetDateTime.now())")
  @Mapping(
      target = "description",
      expression =
          "java(postWithdrawalRequestBody.getDescription().isPresent() ? postWithdrawalRequestBody.getDescription().get() : \"withdrawal\")")
  CreateTransactionInput map(
      UUID organizationId, PostWithdrawalRequestBody postWithdrawalRequestBody);

  PostWithdrawalResponseBody mapToPostWithdrawalResponseBody(Transaction transaction);

  GetTransactionsResponseBody map(Page<TransactionEnriched> transactions);
}
