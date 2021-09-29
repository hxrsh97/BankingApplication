package com.banking.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class SendDto {

  private final UUID creditorId;
  private final UUID debtorId;
  private final BigDecimal amount;

  public SendDto(UUID creditorId, UUID debtorId, BigDecimal amount) {
    this.creditorId = creditorId;
    this.debtorId = debtorId;
    this.amount = amount;
  }

  public UUID getCreditorId() {
    return creditorId;
  }

  public UUID getDebtorId() {
    return debtorId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SendDto sendDto = (SendDto) o;
    return creditorId.equals(sendDto.creditorId) && debtorId.equals(sendDto.debtorId) && amount.equals(sendDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creditorId, debtorId, amount);
  }
}
