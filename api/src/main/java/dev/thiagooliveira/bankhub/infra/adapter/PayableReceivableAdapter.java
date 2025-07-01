package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PaymentPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PayableReceivableRepository;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PayableReceivableTemplateRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PayableReceivableAdapter implements PayableReceivablePort {

  private final PayableReceivableRepository payableReceivableRepository;
  private final PayableReceivableTemplateRepository payableReceivableTemplateRepository;
  private final PaymentPort paymentPort;

  public PayableReceivableAdapter(
      PayableReceivableRepository payableReceivableRepository,
      PayableReceivableTemplateRepository payableReceivableTemplateRepository,
      PaymentPort paymentPort) {
    this.payableReceivableRepository = payableReceivableRepository;
    this.payableReceivableTemplateRepository = payableReceivableTemplateRepository;
    this.paymentPort = paymentPort;
  }

  @Override
  public PayableReceivable update(
      UUID id, Optional<BigDecimal> amount, Optional<LocalDate> dueDate) {
    return this.payableReceivableRepository
        .findById(id)
        .map(p -> p.update(amount, dueDate))
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            })
        .orElseThrow();
  }

  @Override
  public PayableReceivable update(PayableReceivable payableReceivable) {
    return Optional.of(
            this.payableReceivableRepository.save(PayableReceivableEntity.from(payableReceivable)))
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            })
        .orElseThrow();
  }

  @Override
  public Optional<PayableReceivable> findByIdAndAccountId(UUID id, UUID accountId) {
    return this.payableReceivableRepository
        .findByIdAndAccountId(id, accountId)
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            });
  }

  @Override
  public PayableReceivable create(CreatePayableReceivableEnrichedInput input) {
    PayableReceivableEntity entity = PayableReceivableEntity.from(input);
    return Optional.of(this.payableReceivableRepository.save(entity))
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              return p.toDomain(template, Optional.empty());
            })
        .orElseThrow();
  }

  @Override
  public Optional<PayableReceivable> findByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.payableReceivableRepository
        .findByIdAndOrganizationId(id, organizationId)
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            });
  }

  @Override
  public List<PayableReceivable> findByOrganizationId(
      UUID organizationId, LocalDate from, LocalDate to) {
    return this.payableReceivableRepository
        .findByOrganizationIdOrderByDueDateAsc(organizationId, from, to)
        .stream()
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            })
        .toList();
  }

  @Override
  public List<PayableReceivable> findByTemplateIdInAndDueDateBetween(
      List<UUID> templateIds, LocalDate from, LocalDate to) {
    return this.payableReceivableRepository
        .findByTemplateIdInAndDueDateBetween(templateIds, from, to)
        .stream()
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            })
        .toList();
  }

  @Override
  public Optional<PayableReceivable> findByTemplateIdAndDueDate(
      UUID templateId, LocalDate dueDate) {
    return this.payableReceivableRepository
        .findByTemplateIdAndDueDate(templateId, dueDate)
        .map(
            p -> {
              var template =
                  this.payableReceivableTemplateRepository
                      .findById(p.getTemplateId())
                      .orElseThrow();
              var paymentId =
                  this.paymentPort.findByPayableReceivableId(p.getId()).stream()
                      .map(Payment::id)
                      .findFirst();
              return p.toDomain(template, paymentId);
            });
  }

  @Override
  public boolean existsByTemplateIdAndDueDate(UUID templateId, LocalDate dueDate) {
    return this.payableReceivableRepository.existsByTemplateIdAndDueDateOriginal(
        templateId, dueDate);
  }
}
