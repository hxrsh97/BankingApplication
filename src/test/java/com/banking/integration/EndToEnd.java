package com.banking.integration;

import com.banking.dto.ImmutableSendDto;
import com.banking.dto.SendDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEnd {

  @Test
  @DisplayName("Complete end to end test checking transactions made between two accounts " +
    "with correct resulting balances")
  void testEndToEnd(@Autowired WebTestClient webTestClient) {

    String statementUri = "api/v1/statement/generate?accountId=";
    String transactionUri = "api/v1/transaction/send";

    ImmutableSendDto sendDto = ImmutableSendDto.builder()
      .creditorId(UUID.fromString("19c0bb3a-221c-4254-a303-807ffd12750e"))
      .debtorId(UUID.fromString("19fc08e9-a389-45a8-bb06-2cdfe7ad27e6"))
      .amount(new BigDecimal("5"))
      .build();

    webTestClient
      .get()
      .uri(statementUri + UUID.fromString("19c0bb3a-221c-4254-a303-807ffd12750e"))
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .jsonPath("$.balance")
      .isEqualTo(new BigDecimal("10.0"))
      .jsonPath("$.transactionList[0].transactionId")
      .exists()
      .jsonPath("$.transactionList[0].accountId")
      .isEqualTo("19c0bb3a-221c-4254-a303-807ffd12750e")
      .jsonPath("$.transactionList[0].timestamp")
      .exists()
      .jsonPath("$.transactionList[0].value")
      .isEqualTo(new BigDecimal("10.0"))
      .jsonPath("$.transactionList[0].balance")
      .isEqualTo(new BigDecimal("10.0"));

    webTestClient
      .post()
      .uri(transactionUri)
      .body(Mono.just(sendDto), SendDto.class)
      .exchange()
      .expectStatus()
      .isOk();

    webTestClient
      .get()
      .uri(statementUri + UUID.fromString("19fc08e9-a389-45a8-bb06-2cdfe7ad27e6"))
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .jsonPath("$.balance")
      .isEqualTo(new BigDecimal("5.0"))
      .jsonPath("$.transactionList[0].transactionId")
      .exists()
      .jsonPath("$.transactionList[0].accountId")
      .isEqualTo("19fc08e9-a389-45a8-bb06-2cdfe7ad27e6")
      .jsonPath("$.transactionList[0].timestamp")
      .exists()
      .jsonPath("$.transactionList[0].value")
      .isEqualTo(new BigDecimal("5.0"))
      .jsonPath("$.transactionList[0].balance")
      .isEqualTo(new BigDecimal("5.0"));

    webTestClient
      .get()
      .uri(statementUri + UUID.fromString("19c0bb3a-221c-4254-a303-807ffd12750e"))
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .jsonPath("$.balance")
      .isEqualTo(new BigDecimal("5.0"))
      .jsonPath("$.transactionList[1].transactionId")
      .exists()
      .jsonPath("$.transactionList[1].accountId")
      .isEqualTo("19c0bb3a-221c-4254-a303-807ffd12750e")
      .jsonPath("$.transactionList[1].timestamp")
      .exists()
      .jsonPath("$.transactionList[1].value")
      .isEqualTo(new BigDecimal("-5.0"))
      .jsonPath("$.transactionList[1].balance")
      .isEqualTo(new BigDecimal("5.0"));
  }
}
