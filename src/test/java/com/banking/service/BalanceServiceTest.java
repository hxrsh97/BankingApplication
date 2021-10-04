package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

  @Mock
  TransactionRepository transactionRepository;

  private BalanceService balanceService;

  @BeforeEach
  void setup() {
    balanceService = new BalanceService(transactionRepository);
  }

  @Test
  void getBalanceNoTransactionsInSystemForUser() {

    var balance = balanceService.getBalance(UUID.randomUUID());

    assertThat(balance).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  void getBalanceTransactionsExist() {

    var accountId = UUID.randomUUID();
    var transaction = new Transaction(UUID.randomUUID(), accountId, Instant.now(), BigDecimal.ZERO, BigDecimal.TEN);

    when(transactionRepository.findTopByAccountIdOrderByTimestampDesc(accountId)).thenReturn(Optional.of(transaction));

    var balance = balanceService.getBalance(accountId);
    assertThat(balance).isEqualTo(BigDecimal.TEN);
  }
}