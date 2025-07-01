package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import java.util.List;
import java.util.UUID;

public interface PaymentPort {

  Payment create(CreatePaymentInput input);

  List<Payment> findByPayableReceivableId(UUID payableReceivableId);
}
