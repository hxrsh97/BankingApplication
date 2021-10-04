package com.banking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value.Immutable
@JsonDeserialize(as = ImmutableSendDto.class)
@JsonSerialize(as = ImmutableSendDto.class)
public interface SendDto {

  UUID creditorId();

  UUID debtorId();

  BigDecimal amount();
}
