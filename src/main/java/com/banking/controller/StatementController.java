package com.banking.controller;

import com.banking.dto.StatementDto;
import com.banking.service.AccountService;
import com.banking.service.StatementService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/statement")
public class StatementController {

  private final StatementService statementService;
  private final AccountService accountService;

  public StatementController(StatementService statementService, AccountService accountService) {
    this.statementService = statementService;
    this.accountService = accountService;
  }

  @GetMapping("/generate")
  @ResponseBody
  public StatementDto getStatements(@RequestParam UUID accountId) {
    if (accountService.checkExists(accountId)) {
      return statementService.generate(accountId);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }
  }
}
