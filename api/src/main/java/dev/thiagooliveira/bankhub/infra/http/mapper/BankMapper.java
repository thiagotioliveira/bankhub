package dev.thiagooliveira.bankhub.infra.http.mapper;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import dev.thiagooliveira.bankhub.spec.http.dto.GetBankResponseBody;
import dev.thiagooliveira.bankhub.spec.http.dto.PostBankRequestBody;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface BankMapper {

  GetBankResponseBody map(Bank bank);

  CreateBankInput map(UUID organizationId, PostBankRequestBody postBankRequestBody);
}
