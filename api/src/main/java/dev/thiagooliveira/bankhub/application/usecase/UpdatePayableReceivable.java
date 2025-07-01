package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.UpdatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;

public class UpdatePayableReceivable {

  private final PayableReceivablePort payableReceivablePort;

  public UpdatePayableReceivable(PayableReceivablePort payableReceivablePort) {
    this.payableReceivablePort = payableReceivablePort;
  }

  public void update(UpdatePayableReceivableInput updatePayableReceivableInput) {
    var p =
        this.payableReceivablePort
            .findByIdAndOrganizationId(
                updatePayableReceivableInput.payableReceivableId(),
                updatePayableReceivableInput.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("payable/receivable not found"));

    this.payableReceivablePort.update(
        updatePayableReceivableInput.payableReceivableId(),
        updatePayableReceivableInput.amount(),
        updatePayableReceivableInput.dueDate());
  }
}
