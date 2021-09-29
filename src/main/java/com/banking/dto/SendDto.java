package com.banking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value.Immutable
@JsonSerialize(as = ImmutableSendDto.class)
@JsonDeserialize(as = ImmutableSendDto.class)
public interface SendDto {

  UUID creditorId();

  UUID debtorId();

  BigDecimal amount();
}
