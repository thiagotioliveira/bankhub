package dev.thiagooliveira.bankhub.infra.http.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import dev.thiagooliveira.bankhub.spec.http.dto.GetAccountResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.GetAccountsResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostAccountRequestBody;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(uses = {CurrencyMapper.class})
public interface AccountMapper {

  GetAccountsResponseBody map(Account account);

  GetAccountResponseBody map(AccountEnriched account);

  CreateAccountInput map(UUID organizationId, PostAccountRequestBody postAccountRequestBody);

  default Currency map(String currency) {
    return Currency.valueOf(currency);
  }
}
