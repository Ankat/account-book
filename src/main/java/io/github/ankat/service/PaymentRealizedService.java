package io.github.ankat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ankat.entity.Account;
import io.github.ankat.entity.PaymentRealized;
import io.github.ankat.entity.PaymentTransaction;
import io.github.ankat.enums.PaymentRealizedStatus;
import io.github.ankat.model.*;
import io.github.ankat.repository.PaymentRealizedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service(value = "paymentRealizedService")
public class PaymentRealizedService {

    private final ObjectMapper objectMapper;
    private final AccountService accountService;
    private final PaymentRealizedRepository paymentRealizedRepository;
    private final TransactionFlowService transactionFlowService;
    private final PaymentTransactionService paymentTransactionService;

    public List<PaymentRealizedModel> getPaymentRealizedByAccountNo(String accountNo) {
        List<PaymentRealized> paymentRealizeds = paymentRealizedRepository.findByAccountAccountNo(accountNo);
        return paymentRealizeds.stream().map(paymentRealized -> objectMapper.convertValue(paymentRealized, PaymentRealizedModel.class)).collect(Collectors.toList());
    }

    public List<PaymentRealizedModel> getPaymentRealizedByAccountNoAndPaymentRealizedStatus(String accountNo, PaymentRealizedStatus paymentRealizedStatus) {
        List<PaymentRealized> paymentRealizeds = paymentRealizedRepository.findByAccountAccountNoAndPaymentRealizedStatus(accountNo, paymentRealizedStatus);
        return paymentRealizeds.stream().map(paymentRealized -> objectMapper.convertValue(paymentRealized, PaymentRealizedModel.class)).collect(Collectors.toList());
    }

    @Transactional
    public Optional<PaymentRealizedModel> savePaymentRealized(PaymentRealizedModel paymentRealizedModel) {
        PaymentRealized paymentRealized = objectMapper.convertValue(paymentRealizedModel, PaymentRealized.class);
        if (accountService.getAccountByAccountNo(paymentRealized.getAccount().getAccountNo()).isPresent()) {
            paymentRealized.setPaymentRealizedStatus(PaymentRealizedStatus.PAYMENT_NOT_REALIZED);
            paymentRealized = paymentRealizedRepository.save(paymentRealized);
            if (paymentRealized != null) {
                return Optional.of(objectMapper.convertValue(paymentRealized, PaymentRealizedModel.class));
            }
        }

        return Optional.empty();
    }

    @Transactional
    public Map<String, AccountDetail> startPaymentRealized(AccountModel fromAccountModel) {
        Map<String, AccountDetail> responseMap = new HashMap<String, AccountDetail>();
        Boolean flag = recursivePaymentRealized(fromAccountModel, new ArrayList<Boolean>());
        if (flag) {
            List<AccountModel> accountModels = accountService.getAccounts();
            accountModels.stream().forEach(accountModel -> System.out.println(accountModel));
            accountModels.stream().forEach(accountModel -> {
                List<PaymentRealizedModel> paymentRealizedModels = getPaymentRealizedByAccountNoAndPaymentRealizedStatus(accountModel.getAccountNo(), PaymentRealizedStatus.PAYMENT_NOT_REALIZED);
                AccountDetail accountDetail = new AccountDetail();
                accountDetail.setAmount(accountModel.getAmount());
                accountDetail.setPaymentRealizedModels(paymentRealizedModels);
                responseMap.put(accountModel.getAccountName(),accountDetail);
            });
            return responseMap;
        }
        return responseMap;
    }


    public Boolean recursivePaymentRealized(AccountModel fromAccountModel, List<Boolean> booleanFlags) {
        Optional<TransactionFlowModel> optionalTransactionFlowModel = transactionFlowService.getTransactionFlowByFromAccount(fromAccountModel);
        if (optionalTransactionFlowModel.isPresent()) {
            List<PaymentRealizedModel> paymentRealizedModels = getPaymentRealizedByAccountNoAndPaymentRealizedStatus(fromAccountModel.getAccountNo(), PaymentRealizedStatus.PAYMENT_NOT_REALIZED);//getPaymentRealizedByAccountNo(fromAccountModel.getAccountNo());
            if (paymentRealizedModels == null || paymentRealizedModels.isEmpty() || paymentRealizedModels.size() == 0) {
                booleanFlags.add(Boolean.TRUE);
            }
            if (booleanFlags.size() > accountService.getAccounts().size()) {
                return Boolean.TRUE;
            }
            Optional<PaymentRealizedModel> maxOptionalPaymentRealizedModel = paymentRealizedModels.stream()
                    .filter(paymentRealizedModel -> paymentRealizedModel.getPaymentRealizedStatus().equals(PaymentRealizedStatus.PAYMENT_NOT_REALIZED))
                    .filter(paymentRealizedModel -> paymentRealizedModel.getRealizedAmount().intValue() <= fromAccountModel.getAmount().intValue())
                    .max((o1, o2) -> Integer.compare(o1.getRealizedAmount(), o2.getRealizedAmount()));
            if (maxOptionalPaymentRealizedModel.isPresent()) {
                System.out.println("From : " + optionalTransactionFlowModel.get().getFromAccount().getAccountName() + " To : " + optionalTransactionFlowModel.get().getToAccount().getAccountName() + " Payment Realized : " + maxOptionalPaymentRealizedModel.get().getRealizedAmount());
                Account fromAccount = updatePaymentRealized(optionalTransactionFlowModel.get().getFromAccount(), optionalTransactionFlowModel.get().getToAccount(), maxOptionalPaymentRealizedModel.get());
                recursivePaymentRealized(objectMapper.convertValue(fromAccount, AccountModel.class), booleanFlags);
            } else {
                recursivePaymentRealized(optionalTransactionFlowModel.get().getToAccount(), booleanFlags);
            }
            return Boolean.TRUE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Transactional
    private Account updatePaymentRealized(AccountModel fromAccountModel, AccountModel toAccountModel, PaymentRealizedModel paymentRealizedModel) {
        PaymentRealized paymentRealized = objectMapper.convertValue(paymentRealizedModel, PaymentRealized.class);
        Account fromAccount = objectMapper.convertValue(fromAccountModel, Account.class);
        Account toAccount = objectMapper.convertValue(toAccountModel, Account.class);

        Optional<PaymentTransactionModel> optionalFromLastPaymentTransactionModel = paymentTransactionService.getLastUpdatePaymentTransactionForAccountNo(fromAccount.getAccountNo());
        Optional<PaymentTransactionModel> optionalToLastPaymentTransactionModel = paymentTransactionService.getLastUpdatePaymentTransactionForAccountNo(toAccount.getAccountNo());

        PaymentTransaction fromPaymentTransaction = new PaymentTransaction();
        fromPaymentTransaction.setAccount(fromAccount);
        fromPaymentTransaction.setOpeningBalance(optionalFromLastPaymentTransactionModel.get().getClosingBalance());
        fromPaymentTransaction.setDebitAmount(paymentRealized.getRealizedAmount());
        Optional<PaymentTransactionModel> optionalFromPaymentTransactionModel = paymentTransactionService.savePaymentTransaction(objectMapper.convertValue(fromPaymentTransaction, PaymentTransactionModel.class));

        fromAccount.setAmount(optionalFromPaymentTransactionModel.get().getClosingBalance());
        accountService.saveAccount(objectMapper.convertValue(fromAccount, AccountModel.class));

        PaymentTransaction toPaymentTransaction = new PaymentTransaction();
        toPaymentTransaction.setAccount(toAccount);
        toPaymentTransaction.setOpeningBalance(optionalToLastPaymentTransactionModel.get().getClosingBalance());
        toPaymentTransaction.setCreditAmount(paymentRealized.getRealizedAmount());
        Optional<PaymentTransactionModel> toPaymentTransactionModel = paymentTransactionService.savePaymentTransaction(objectMapper.convertValue(toPaymentTransaction, PaymentTransactionModel.class));

        toAccount.setAmount(toPaymentTransactionModel.get().getClosingBalance());
        accountService.saveAccount(objectMapper.convertValue(toAccount, AccountModel.class));

        paymentRealized.setPaymentRealizedStatus(PaymentRealizedStatus.PAYMENT_REALIZED);
        paymentRealizedRepository.save(paymentRealized);
        return fromAccount;
    }
}
