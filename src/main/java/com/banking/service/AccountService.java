package com.banking.service;

import com.banking.db.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public boolean checkExists(UUID accountId) {
    return accountRepository.findById(accountId).isPresent();
  }
}
