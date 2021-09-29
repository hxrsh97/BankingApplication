package com.banking.dto;

import com.banking.model.Transaction;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableStatementDto.class)
@JsonSerialize(as =  ImmutableStatementDto.class)
public interface StatementDto {

  BigDecimal balance();

  List<Transaction> transactionList();
}
