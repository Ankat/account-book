package io.github.ankat;

import io.github.ankat.model.AccountModel;
import io.github.ankat.model.PaymentRealizedModel;
import io.github.ankat.model.TransactionFlowModel;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountBookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void getAccounts() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/api/accounts",
                HttpMethod.GET, entity, List.class);
        System.out.println(response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    public void getAccountByAccountNo() {
        AccountModel accountModelA = restTemplate.getForObject(getRootUrl() + "/api/accounts/1111", AccountModel.class);
        assertNotNull(accountModelA);
    }

    @Test
    public void saveAccount() {
        ResponseEntity<AccountModel> postResponse = null;
        ResponseEntity<TransactionFlowModel> postResponseTransactionFlowModel = null;
        AccountModel accountModelA = new AccountModel();
        accountModelA.setAccountNo(UUID.randomUUID().toString());
        accountModelA.setAccountName("Account A");
        accountModelA.setAmount(123);

        AccountModel accountModelB = new AccountModel();
        accountModelB.setAccountNo(UUID.randomUUID().toString());
        accountModelB.setAccountName("Account B");
        accountModelB.setAmount(100);

        AccountModel accountModelC = new AccountModel();
        accountModelC.setAccountNo(UUID.randomUUID().toString());
        accountModelC.setAccountName("Account C");
        accountModelC.setAmount(50);

        postResponse = restTemplate.postForEntity(getRootUrl() + "/api/accounts", accountModelA, AccountModel.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        accountModelA = postResponse.getBody();

        postResponse = restTemplate.postForEntity(getRootUrl() + "/api/accounts", accountModelB, AccountModel.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        accountModelB = postResponse.getBody();

        postResponse = restTemplate.postForEntity(getRootUrl() + "/api/accounts", accountModelC, AccountModel.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        accountModelC = postResponse.getBody();

        assertEquals(accountModelA.getAccountName(),"Account A");
        assertEquals(accountModelB.getAccountName(),"Account B");
        assertEquals(accountModelC.getAccountName(),"Account C");

        TransactionFlowModel transactionFlowModelAB = new TransactionFlowModel();
        transactionFlowModelAB.setFromAccount(accountModelA);
        transactionFlowModelAB.setToAccount(accountModelB);

        TransactionFlowModel transactionFlowModelBC = new TransactionFlowModel();
        transactionFlowModelBC.setFromAccount(accountModelB);
        transactionFlowModelBC.setToAccount(accountModelC);

        TransactionFlowModel transactionFlowModelCA = new TransactionFlowModel();
        transactionFlowModelCA.setFromAccount(accountModelC);
        transactionFlowModelCA.setToAccount(accountModelA);


        postResponseTransactionFlowModel = restTemplate.postForEntity(getRootUrl() + "/api/transaction-flows", transactionFlowModelAB, TransactionFlowModel.class);
        assertNotNull(postResponseTransactionFlowModel);
        assertNotNull(postResponseTransactionFlowModel.getBody());

        postResponseTransactionFlowModel = restTemplate.postForEntity(getRootUrl() + "/api/transaction-flows", transactionFlowModelBC, TransactionFlowModel.class);
        assertNotNull(postResponseTransactionFlowModel);
        assertNotNull(postResponseTransactionFlowModel.getBody());

        postResponseTransactionFlowModel = restTemplate.postForEntity(getRootUrl() + "/api/transaction-flows", transactionFlowModelCA, TransactionFlowModel.class);
        assertNotNull(postResponseTransactionFlowModel);
        assertNotNull(postResponseTransactionFlowModel.getBody());

        List<PaymentRealizedModel> paymentRealizedModels = new ArrayList<PaymentRealizedModel>();
        PaymentRealizedModel paymentRealizedModelA1 = new PaymentRealizedModel();
        paymentRealizedModelA1.setAccount(accountModelA);
        paymentRealizedModelA1.setRealizedAmount(1);
        paymentRealizedModels.add(paymentRealizedModelA1);

        PaymentRealizedModel paymentRealizedModelA40 = new PaymentRealizedModel();
        paymentRealizedModelA40.setAccount(accountModelA);
        paymentRealizedModelA40.setRealizedAmount(40);
        paymentRealizedModels.add(paymentRealizedModelA40);

        PaymentRealizedModel paymentRealizedModelA50 = new PaymentRealizedModel();
        paymentRealizedModelA50.setAccount(accountModelA);
        paymentRealizedModelA50.setRealizedAmount(50);
        paymentRealizedModels.add(paymentRealizedModelA50);

        PaymentRealizedModel paymentRealizedModelA100 = new PaymentRealizedModel();
        paymentRealizedModelA100.setAccount(accountModelA);
        paymentRealizedModelA100.setRealizedAmount(100);
        paymentRealizedModels.add(paymentRealizedModelA100);

        PaymentRealizedModel paymentRealizedModelB100 = new PaymentRealizedModel();
        paymentRealizedModelB100.setAccount(accountModelB);
        paymentRealizedModelB100.setRealizedAmount(100);
        paymentRealizedModels.add(paymentRealizedModelB100);

        PaymentRealizedModel paymentRealizedModelB50 = new PaymentRealizedModel();
        paymentRealizedModelB50.setAccount(accountModelB);
        paymentRealizedModelB50.setRealizedAmount(50);
        paymentRealizedModels.add(paymentRealizedModelB50);

        PaymentRealizedModel paymentRealizedModelB40 = new PaymentRealizedModel();
        paymentRealizedModelB40.setAccount(accountModelB);
        paymentRealizedModelB40.setRealizedAmount(40);
        paymentRealizedModels.add(paymentRealizedModelB40);

        PaymentRealizedModel paymentRealizedModelB30 = new PaymentRealizedModel();
        paymentRealizedModelB30.setAccount(accountModelB);
        paymentRealizedModelB30.setRealizedAmount(30);
        paymentRealizedModels.add(paymentRealizedModelB30);

        PaymentRealizedModel paymentRealizedModelB10 = new PaymentRealizedModel();
        paymentRealizedModelB10.setAccount(accountModelB);
        paymentRealizedModelB10.setRealizedAmount(10);
        paymentRealizedModels.add(paymentRealizedModelB10);

        PaymentRealizedModel paymentRealizedModelC50 = new PaymentRealizedModel();
        paymentRealizedModelC50.setAccount(accountModelC);
        paymentRealizedModelC50.setRealizedAmount(50);
        paymentRealizedModels.add(paymentRealizedModelC50);

        PaymentRealizedModel paymentRealizedModelC60 = new PaymentRealizedModel();
        paymentRealizedModelC60.setAccount(accountModelC);
        paymentRealizedModelC60.setRealizedAmount(60);
        paymentRealizedModels.add(paymentRealizedModelC60);

        PaymentRealizedModel paymentRealizedModelC80 = new PaymentRealizedModel();
        paymentRealizedModelC80.setAccount(accountModelC);
        paymentRealizedModelC80.setRealizedAmount(80);
        paymentRealizedModels.add(paymentRealizedModelC80);

        PaymentRealizedModel paymentRealizedModelC100 = new PaymentRealizedModel();
        paymentRealizedModelC100.setAccount(accountModelC);
        paymentRealizedModelC100.setRealizedAmount(100);
        paymentRealizedModels.add(paymentRealizedModelC100);

        paymentRealizedModels.stream().forEach(paymentRealizedModel -> {
            ResponseEntity<PaymentRealizedModel> postResponsePaymentRealizedModel = restTemplate.postForEntity(getRootUrl() + "/api/payment-realizeds", paymentRealizedModel, PaymentRealizedModel.class);
            assertNotNull(postResponsePaymentRealizedModel);
            assertNotNull(postResponsePaymentRealizedModel.getBody());
        });

        ResponseEntity<Map> postForEntity = restTemplate.postForEntity(getRootUrl() + "/api/payment-realizeds/"+accountModelA.getAccountNo(), null, Map.class);
        assertNotNull(postForEntity);
        assertNotNull(postForEntity.getBody());

        System.out.println(postForEntity.getBody());
    }
}
