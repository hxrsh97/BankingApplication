package com.banking.controller;

import com.banking.db.AccountRepository;
import com.banking.db.TransactionRepository;
import com.banking.dto.SendDto;
import com.banking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/v1/transaction")
public class TransactionController {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final TransactionService transactionService;

  public TransactionController(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionService transactionService) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.transactionService = transactionService;
  }

  @PostMapping("/send")
  public void send(@RequestBody SendDto sendDto) {
    accountRepository.findById(sendDto.creditorId()).ifPresentOrElse(account -> {
        BigDecimal balance = transactionRepository.findTopByAccountIdOrderByTimestampDesc(sendDto.creditorId())
          .getBalance();
        if (balance.subtract(sendDto.amount()).signum() > 0) {
          transactionService.process(sendDto, balance);
        } else {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creditor account balance is insufficient");
        }
      },
      () -> {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creditor account does not exist");
      });
  }
}
