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

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Transactional
  public void process(SendDto sendDto, BigDecimal balance) {
    var transactionDeduct = new Transaction(UUID.randomUUID(), sendDto.creditorId(), Instant.now(), sendDto.amount().negate(), balance);
    BigDecimal debtorBalance = transactionRepository.findTopByAccountIdOrderByTimestampDesc(sendDto.debtorId()).getBalance();
    var transactionAdd = new Transaction(UUID.randomUUID(), sendDto.debtorId(), Instant.now(), sendDto.amount(), debtorBalance.add(sendDto.amount()));
  }
}
