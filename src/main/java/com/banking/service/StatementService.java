package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.dto.ImmutableStatementDto;
import com.banking.dto.StatementDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatementService {

  private final TransactionRepository transactionRepository;

  public StatementService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public StatementDto generate(UUID accountId) {
    var balance = transactionRepository.findTopByAccountIdOrderByTimestampDesc(accountId).getBalance();
    var transactions = transactionRepository.findAllByAccountId(accountId);

    return ImmutableStatementDto.builder().balance(balance).transactionList(transactions).build();
  }
}
