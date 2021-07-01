package io.github.ankat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ankat.entity.PaymentTransaction;
import io.github.ankat.model.PaymentTransactionModel;
import io.github.ankat.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service(value = "paymentTransactionService")
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final ObjectMapper objectMapper;

    public Optional<PaymentTransactionModel> getLastUpdatePaymentTransactionForAccountNo(String accountNo) {
        Optional<PaymentTransaction> optionalPaymentTransaction = paymentTransactionRepository.findFirstByAccountAccountNoOrderByPaymentIdDesc(accountNo);
        if (optionalPaymentTransaction.isPresent()) {
            return Optional.of(objectMapper.convertValue(optionalPaymentTransaction.get(), PaymentTransactionModel.class));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<PaymentTransactionModel> savePaymentTransaction(PaymentTransactionModel paymentTransactionModel) {
        PaymentTransaction paymentTransaction = objectMapper.convertValue(paymentTransactionModel, PaymentTransaction.class);
        calculateOpeningClosingBalance(paymentTransaction);
        paymentTransaction = paymentTransactionRepository.save(paymentTransaction);
        if (paymentTransaction != null) {
            return Optional.of(objectMapper.convertValue(paymentTransaction, PaymentTransactionModel.class));
        }
        return Optional.empty();
    }

    private void calculateOpeningClosingBalance(PaymentTransaction paymentTransaction) {
        Integer closingBalance = paymentTransaction.getOpeningBalance() - paymentTransaction.getDebitAmount() + paymentTransaction.getCreditAmount();
        paymentTransaction.setClosingBalance(closingBalance);
    }

}
