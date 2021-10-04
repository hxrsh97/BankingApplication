package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.dto.StatementDto;
import com.banking.model.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatementServiceTest {

  @Mock
  BalanceService balanceService;

  @Mock
  TransactionRepository transactionRepository;

  private StatementService statementService;

  @BeforeEach
  void setup() {
    statementService = new StatementService(balanceService, transactionRepository);
  }

  @Test
  void generateStatement() {

    var accountId = UUID.randomUUID();
    Transaction transaction = new Transaction(UUID.randomUUID(), accountId, Instant.now(), BigDecimal.TEN, BigDecimal.TEN);


    when(balanceService.getBalance(accountId)).thenReturn(BigDecimal.TEN);
    when(transactionRepository.findAllByAccountId(accountId)).thenReturn(List.of(transaction));

    StatementDto statementDto = statementService.generate(accountId);

    assertThat(statementDto.balance()).isEqualTo(BigDecimal.TEN);
    assertThat(statementDto.transactionList()).isEqualTo(List.of(transaction));
  }
}