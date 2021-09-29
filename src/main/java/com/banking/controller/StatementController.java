package com.banking.controller;

import com.banking.dto.StatementDto;
import com.banking.service.StatementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/statement")
public class StatementController {

  private final StatementService statementService;

  public StatementController(StatementService statementService) {
    this.statementService = statementService;
  }

  @GetMapping("/generate")
  @ResponseBody
  public StatementDto getStatements(@RequestParam UUID accountId) {
    return statementService.generate(accountId);
  }
}
