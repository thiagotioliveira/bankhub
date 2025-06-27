package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateReceivable;
import dev.thiagooliveira.bankhub.domain.dto.CreateReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.Receivable;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceivableService {

  private final CreateReceivable createReceivable;
  private final CategoryService categoryService;

  public ReceivableService(CreateReceivable createReceivable, CategoryService categoryService) {
    this.createReceivable = createReceivable;
    this.categoryService = categoryService;
  }

  @Transactional
  public List<Receivable> create(CreateReceivableInput input) {
    var category =
        this.categoryService
            .findById(input.categoryId())
            .orElseThrow(() -> new BusinessLogicException("category not found"));
    return this.createReceivable.create(input.enrichWith(category));
  }
}
