package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;

public interface BankPort {

  Bank create(CreateBankInput input);
}
