package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateBank;
import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import org.springframework.stereotype.Service;

@Service
public class BankService {

  private final CreateBank createBank;

  public BankService(CreateBank createBank) {
    this.createBank = createBank;
  }

  public Bank create(CreateBankInput input) {
    return this.createBank.create(input);
  }
}
