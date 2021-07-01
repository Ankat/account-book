package io.github.ankat.listener;

import io.github.ankat.model.AccountModel;
import io.github.ankat.model.PaymentRealizedModel;
import io.github.ankat.model.TransactionFlowModel;
import io.github.ankat.service.AccountService;
import io.github.ankat.service.PaymentRealizedService;
import io.github.ankat.service.TransactionFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class InitialBalancerLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountService accountService;
    private final TransactionFlowService transactionFlowService;
    private final PaymentRealizedService paymentRealizedService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        AccountModel accountModelA = new AccountModel();
        accountModelA.setAccountNo("1111");
        accountModelA.setAccountName("Account A");
        accountModelA.setAmount(123);

        AccountModel accountModelB = new AccountModel();
        accountModelB.setAccountNo("2222");
        accountModelB.setAccountName("Account B");
        accountModelB.setAmount(100);

        AccountModel accountModelC = new AccountModel();
        accountModelC.setAccountNo("3333");
        accountModelC.setAccountName("Account C");
        accountModelC.setAmount(50);

        Optional<AccountModel> optionalAccountModelA = accountService.saveAccount(accountModelA);
        Optional<AccountModel> optionalAccountModelB = accountService.saveAccount(accountModelB);
        Optional<AccountModel> optionalAccountModelC = accountService.saveAccount(accountModelC);

        TransactionFlowModel transactionFlowModelA = new TransactionFlowModel();
        transactionFlowModelA.setFromAccount(optionalAccountModelA.get());
        transactionFlowModelA.setToAccount(optionalAccountModelB.get());

        TransactionFlowModel transactionFlowModelB = new TransactionFlowModel();
        transactionFlowModelB.setFromAccount(optionalAccountModelB.get());
        transactionFlowModelB.setToAccount(optionalAccountModelC.get());

        TransactionFlowModel transactionFlowModelC = new TransactionFlowModel();
        transactionFlowModelC.setFromAccount(optionalAccountModelC.get());
        transactionFlowModelC.setToAccount(optionalAccountModelA.get());

        Optional<TransactionFlowModel> optionalTransactionFlowModelA = transactionFlowService.saveTransactionFlow(transactionFlowModelA);
        Optional<TransactionFlowModel> optionalTransactionFlowModelB = transactionFlowService.saveTransactionFlow(transactionFlowModelB);
        Optional<TransactionFlowModel> optionalTransactionFlowModelC = transactionFlowService.saveTransactionFlow(transactionFlowModelC);

        PaymentRealizedModel paymentRealizedModelA1 = new PaymentRealizedModel();
        paymentRealizedModelA1.setAccount(optionalAccountModelA.get());
        paymentRealizedModelA1.setRealizedAmount(1);

        PaymentRealizedModel paymentRealizedModelA40 = new PaymentRealizedModel();
        paymentRealizedModelA40.setAccount(optionalAccountModelA.get());
        paymentRealizedModelA40.setRealizedAmount(40);

        PaymentRealizedModel paymentRealizedModelA50 = new PaymentRealizedModel();
        paymentRealizedModelA50.setAccount(optionalAccountModelA.get());
        paymentRealizedModelA50.setRealizedAmount(50);

        PaymentRealizedModel paymentRealizedModelA100 = new PaymentRealizedModel();
        paymentRealizedModelA100.setAccount(optionalAccountModelA.get());
        paymentRealizedModelA100.setRealizedAmount(100);


        paymentRealizedService.savePaymentRealized(paymentRealizedModelA1);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelA40);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelA50);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelA100);

        PaymentRealizedModel paymentRealizedModelB100 = new PaymentRealizedModel();
        paymentRealizedModelB100.setAccount(optionalAccountModelB.get());
        paymentRealizedModelB100.setRealizedAmount(100);

        PaymentRealizedModel paymentRealizedModelB50 = new PaymentRealizedModel();
        paymentRealizedModelB50.setAccount(optionalAccountModelB.get());
        paymentRealizedModelB50.setRealizedAmount(50);

        PaymentRealizedModel paymentRealizedModelB40 = new PaymentRealizedModel();
        paymentRealizedModelB40.setAccount(optionalAccountModelB.get());
        paymentRealizedModelB40.setRealizedAmount(40);

        PaymentRealizedModel paymentRealizedModelB30 = new PaymentRealizedModel();
        paymentRealizedModelB30.setAccount(optionalAccountModelB.get());
        paymentRealizedModelB30.setRealizedAmount(30);

        PaymentRealizedModel paymentRealizedModelB10 = new PaymentRealizedModel();
        paymentRealizedModelB10.setAccount(optionalAccountModelB.get());
        paymentRealizedModelB10.setRealizedAmount(10);

        paymentRealizedService.savePaymentRealized(paymentRealizedModelB100);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelB50);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelB40);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelB30);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelB10);

        PaymentRealizedModel paymentRealizedModelC50 = new PaymentRealizedModel();
        paymentRealizedModelC50.setAccount(optionalAccountModelC.get());
        paymentRealizedModelC50.setRealizedAmount(50);

        PaymentRealizedModel paymentRealizedModelC60 = new PaymentRealizedModel();
        paymentRealizedModelC60.setAccount(optionalAccountModelC.get());
        paymentRealizedModelC60.setRealizedAmount(60);

        PaymentRealizedModel paymentRealizedModelC80 = new PaymentRealizedModel();
        paymentRealizedModelC80.setAccount(optionalAccountModelC.get());
        paymentRealizedModelC80.setRealizedAmount(80);

        PaymentRealizedModel paymentRealizedModelC100 = new PaymentRealizedModel();
        paymentRealizedModelC100.setAccount(optionalAccountModelC.get());
        paymentRealizedModelC100.setRealizedAmount(100);

        paymentRealizedService.savePaymentRealized(paymentRealizedModelC50);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelC60);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelC80);
        paymentRealizedService.savePaymentRealized(paymentRealizedModelC100);
    }
}
