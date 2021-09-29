package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "transaction")
public class Transaction {

  @Id
  @Column(name = "transaction_id")
  private UUID transactionId;

  @Column(name = "account_id")
  private UUID accountId;

  @Column(name = "timestamp")
  private Instant timestamp;

  @Column(name = "value")
  private BigDecimal value;

  @Column(name = "balance")
  private BigDecimal balance;

  // For JPA
  protected Transaction() {
  }

  public Transaction(UUID transactionId, UUID accountId, Instant timestamp, BigDecimal value, BigDecimal balance) {
    this.transactionId = transactionId;
    this.accountId = accountId;
    this.timestamp = timestamp;
    this.value = value;
    this.balance = balance;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public BigDecimal getValue() {
    return value;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return transactionId.equals(that.transactionId) && accountId.equals(that.accountId) && timestamp.equals(that.timestamp) && value.equals(that.value) && balance.equals(that.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, accountId, timestamp, value, balance);
  }
}
