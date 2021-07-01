package io.github.ankat.repository;

import io.github.ankat.entity.PaymentRealized;
import io.github.ankat.enums.PaymentRealizedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "paymentRealizedRepository")
public interface PaymentRealizedRepository extends JpaRepository<PaymentRealized, Integer> {
    List<PaymentRealized> findByAccountAccountNo(String accountNo);

    List<PaymentRealized> findByAccountAccountNoAndPaymentRealizedStatus(String accountNo, PaymentRealizedStatus paymentRealizedStatus);
}
