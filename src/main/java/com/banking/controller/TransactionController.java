package com.banking.controller;

import com.banking.db.AccountRepository;
import com.banking.dto.SendDto;
import com.banking.service.BalanceService;
import com.banking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/v1/transaction")
public class TransactionController {

  private final AccountRepository accountRepository;
  private final BalanceService balanceService;
  private final TransactionService transactionService;

  public TransactionController(AccountRepository accountRepository, BalanceService balanceService, TransactionService transactionService) {
    this.accountRepository = accountRepository;
    this.balanceService = balanceService;
    this.transactionService = transactionService;
  }

  @PostMapping(value = "/send", consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void send(@RequestBody SendDto sendDto) {
    accountRepository.findById(sendDto.getCreditorId()).ifPresentOrElse(account -> {
        BigDecimal balance = balanceService.getBalance(sendDto.getCreditorId());
        if (balance.subtract(sendDto.getAmount()).signum() >= 0) {
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
