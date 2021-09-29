package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class BalanceService {

  private final TransactionRepository transactionRepository;

  public BalanceService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public BigDecimal getBalance(UUID accountId) {
    Optional<Transaction> transactionMaybe = transactionRepository.findTopByAccountIdOrderByTimestampDesc(accountId);
    if (transactionMaybe.isPresent()) {
      return transactionMaybe.get().getBalance();
    } else {
      return BigDecimal.ZERO;
    }
  }
}
