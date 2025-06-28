package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayableReceivableService {

  private final CreatePayableReceivable createPayableReceivable;
  private final CategoryService categoryService;

  public PayableReceivableService(
      CreatePayableReceivable createPayableReceivable, CategoryService categoryService) {
    this.createPayableReceivable = createPayableReceivable;
    this.categoryService = categoryService;
  }

  @Transactional
  public List<PayableReceivable> create(CreatePayableReceivableInput input) {
    var category =
        this.categoryService
            .findById(input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    return this.createPayableReceivable.create(input.enrichWith(category));
  }
}
