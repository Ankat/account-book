package io.github.ankat.repository;

import io.github.ankat.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "paymentTransactionRepository")
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {

    Optional<PaymentTransaction> findFirstByAccountAccountNoOrderByPaymentIdDesc(String accountNo);

}
