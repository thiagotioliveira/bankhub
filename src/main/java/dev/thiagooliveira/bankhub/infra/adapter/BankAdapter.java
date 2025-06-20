package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import dev.thiagooliveira.bankhub.domain.port.BankPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.BankEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.BankRepository;
import org.springframework.stereotype.Component;

@Component
public class BankAdapter implements BankPort {

  private final BankRepository bankRepository;

  public BankAdapter(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Bank create(CreateBankInput input) {
    return this.bankRepository.save(BankEntity.from(input)).toDomain();
  }
}
