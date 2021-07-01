package io.github.ankat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ankat.entity.Account;
import io.github.ankat.entity.PaymentTransaction;
import io.github.ankat.model.AccountModel;
import io.github.ankat.model.PaymentTransactionModel;
import io.github.ankat.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service(value = "accountService")
public class AccountService {

    private final AccountRepository accountRepository;
    private final PaymentTransactionService paymentTransactionService;
    private final ObjectMapper objectMapper;

    @Transactional
    public List<AccountModel> getAccounts() {
        List<Account> accounts = accountRepository.findAll().stream().map(account -> {
            Optional<PaymentTransactionModel> optionalPaymentTransactionModel = paymentTransactionService.getLastUpdatePaymentTransactionForAccountNo(account.getAccountNo());
            account.setAmount(optionalPaymentTransactionModel.get().getClosingBalance());
            accountRepository.save(account);
            return account;
        }).collect(Collectors.toList());
        return objectMapper.convertValue(accounts, new TypeReference<List<AccountModel>>() {});
    }

    public Optional<AccountModel> getAccountByAccountNo(String accountNo) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if(optionalAccount.isPresent()){
            Optional<PaymentTransactionModel> optionalPaymentTransactionModel = paymentTransactionService.getLastUpdatePaymentTransactionForAccountNo(optionalAccount.get().getAccountNo());
            optionalAccount.get().setAmount(optionalPaymentTransactionModel.get().getClosingBalance());
            Account account = accountRepository.save(optionalAccount.get());
            return optionalAccount.isPresent() ? Optional.of(objectMapper.convertValue(account, AccountModel.class)) : Optional.empty();
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<AccountModel> saveAccount(AccountModel accountModel) {
        Account account = objectMapper.convertValue(accountModel, Account.class);
        account = accountRepository.save(account);
        if (account != null) {
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setAccount(account);
            paymentTransaction.setCreditAmount(account.getAmount());
            paymentTransactionService.savePaymentTransaction(objectMapper.convertValue(paymentTransaction, PaymentTransactionModel.class));
            return Optional.of(objectMapper.convertValue(account, AccountModel.class));
        }
        return Optional.empty();
    }
}
