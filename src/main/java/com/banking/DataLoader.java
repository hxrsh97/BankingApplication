package com.banking;

import com.banking.db.AccountRepository;
import com.banking.db.TransactionRepository;
import com.banking.model.Account;
import com.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
public class DataLoader implements ApplicationRunner {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public DataLoader(AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    UUID accountId1 = UUID.fromString("19c0bb3a-221c-4254-a303-807ffd12750e");
    UUID accountId2 = UUID.fromString("19fc08e9-a389-45a8-bb06-2cdfe7ad27e6");
    accountRepository.save(new Account(accountId1, "account1"));
    accountRepository.save(new Account(accountId2, "account2"));
    transactionRepository.save(new Transaction(UUID.randomUUID(), accountId1, Instant.now(), BigDecimal.TEN, BigDecimal.TEN));
    transactionRepository.save(new Transaction(UUID.randomUUID(), accountId2, Instant.now(), BigDecimal.TEN, BigDecimal.TEN));
  }
}