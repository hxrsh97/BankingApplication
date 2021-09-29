package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.dto.ImmutableStatementDto;
import com.banking.dto.StatementDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatementService {

  private final BalanceService balanceService;
  private final TransactionRepository transactionRepository;

  public StatementService(BalanceService balanceService, TransactionRepository transactionRepository) {
    this.balanceService = balanceService;
    this.transactionRepository = transactionRepository;
  }

  public StatementDto generate(UUID accountId) {
    return ImmutableStatementDto.builder()
      .balance(balanceService.getBalance(accountId))
      .transactionList(
        transactionRepository.findAllByAccountId(accountId)).build();
  }
}
