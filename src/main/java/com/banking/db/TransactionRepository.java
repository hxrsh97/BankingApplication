package com.banking.db;

import com.banking.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

  Optional<Transaction> findTopByAccountIdOrderByTimestampDesc(UUID accountId);

  List<Transaction> findAllByAccountId(UUID accountId);
}
