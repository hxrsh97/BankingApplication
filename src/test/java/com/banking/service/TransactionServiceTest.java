package com.banking.service;

import com.banking.db.TransactionRepository;
import com.banking.dto.ImmutableSendDto;
import com.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  TransactionRepository transactionRepository;

  @Mock
  BalanceService balanceService;

  private TransactionService transactionService;

  @Captor
  ArgumentCaptor<Transaction> transactionCaptor;


  @BeforeEach
  void setup() {
    transactionService = new TransactionService(transactionRepository, balanceService);
  }

  @Test
  void processTransaction() {

    var creditorId = UUID.randomUUID();
    var debtorId = UUID.randomUUID();
    var amount = BigDecimal.ONE;
    var creditorBalance = BigDecimal.TEN;
    var sendDto = ImmutableSendDto.builder()
      .creditorId(creditorId)
      .debtorId(debtorId)
      .amount(amount)
      .build();
    var transaction = Mockito.mock(Transaction.class);

    when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
    when(balanceService.getBalance(any(UUID.class))).thenReturn(BigDecimal.TEN);

    transactionService.process(sendDto, creditorBalance);

    verify(transactionRepository, times(2)).save(transactionCaptor.capture());
  }
}