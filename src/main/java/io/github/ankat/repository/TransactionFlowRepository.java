package io.github.ankat.repository;

import io.github.ankat.entity.TransactionFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "transactionFlowRepository")
public interface TransactionFlowRepository extends JpaRepository<TransactionFlow, String> {

    Optional<TransactionFlow> findByFromAccountAccountNo(String fromAccountNo);
    Optional<TransactionFlow> findByFromAccountAccountNoAndToAccountAccountNo(String fromAccountNo,String toAccountNo);
}
