package dev.thiagooliveira.bankhub.infra.persistence.entity;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID payableReceivableId;

  @Column(nullable = false, name = "payment_date")
  private OffsetDateTime dateTime;

  @Column(nullable = false)
  private UUID transactionId;

  public PaymentEntity() {}

  public PaymentEntity(
      UUID id, UUID payableReceivableId, OffsetDateTime dateTime, UUID transactionId) {
    this.id = id;
    this.payableReceivableId = payableReceivableId;
    this.dateTime = dateTime;
    this.transactionId = transactionId;
  }

  public static PaymentEntity from(CreatePaymentInput input) {
    PaymentEntity paymentEntity = new PaymentEntity();
    paymentEntity.id = UUID.randomUUID();
    paymentEntity.payableReceivableId = input.payableReceivableId();
    paymentEntity.dateTime = input.dateTime();
    paymentEntity.transactionId = input.transactionId();
    return paymentEntity;
  }

  public Payment toDomain() {
    return new Payment(this.id, this.payableReceivableId, this.transactionId, this.dateTime);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getPayableReceivableId() {
    return payableReceivableId;
  }

  public void setPayableReceivableId(UUID payableReceivableId) {
    this.payableReceivableId = payableReceivableId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }
}
