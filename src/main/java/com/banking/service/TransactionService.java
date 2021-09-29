package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.dto.SendDto;
import com.banking.model.Transaction;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final BalanceService balanceService;

  public TransactionService(TransactionRepository transactionRepository, BalanceService balanceService) {
    this.transactionRepository = transactionRepository;
    this.balanceService = balanceService;
  }

  @Transactional
  public void process(SendDto sendDto, BigDecimal balance) {
    transactionRepository.save(
      new Transaction(UUID.randomUUID(), sendDto.getCreditorId(), Instant.now(), sendDto.getAmount().negate(), balance.subtract(sendDto.getAmount())));
    transactionRepository.save(
      new Transaction(UUID.randomUUID(), sendDto.getDebtorId(), Instant.now(), sendDto.getAmount(),
        balanceService.getBalance(sendDto.getDebtorId()).add(sendDto.getAmount())));
  }
}
