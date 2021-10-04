package com.banking.controller;

import com.banking.dto.SendDto;
import com.banking.service.AccountService;
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

  private final AccountService accountService;
  private final BalanceService balanceService;
  private final TransactionService transactionService;

  public TransactionController(AccountService accountService, BalanceService balanceService, TransactionService transactionService) {
    this.accountService = accountService;
    this.balanceService = balanceService;
    this.transactionService = transactionService;
  }

  @PostMapping(value = "/send", consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public void send(@RequestBody SendDto sendDto) {
    if (accountService.checkExists(sendDto.creditorId())) {
      BigDecimal creditorBalance = balanceService.getBalance(sendDto.creditorId());
      if (creditorBalance.subtract(sendDto.amount()).signum() >= 0) {
        transactionService.process(sendDto, creditorBalance);
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creditor account balance is insufficient");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creditor account does not exist");
    }
  }
}
