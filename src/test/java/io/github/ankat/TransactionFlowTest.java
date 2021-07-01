package io.github.ankat;

import io.github.ankat.model.AccountModel;
import io.github.ankat.model.PaymentRealizedModel;
import io.github.ankat.model.TransactionFlowModel;
import org.junit.jupiter.api.Test;
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

import java.util.List;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountBookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionFlowTest {

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
    public void getPaymentRealizedByAccountNo() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/api/payment-realizeds/1111",
                HttpMethod.GET, entity, List.class);
        assertNotNull(response.getBody());
    }


    @Test
    public void saveTransactionFlow() {
        AccountModel accountModelA = new AccountModel();
        accountModelA.setAccountNo("1111");
        accountModelA.setAccountName("Account A");
        accountModelA.setAmount(123);

        AccountModel accountModelB = new AccountModel();
        accountModelB.setAccountNo("2222");
        accountModelB.setAccountName("Account B");
        accountModelB.setAmount(100);


        ResponseEntity<AccountModel> postResponseAccountModel = restTemplate.postForEntity(getRootUrl() + "/api/accounts", accountModelA, AccountModel.class);
        assertNotNull(postResponseAccountModel);
        assertNotNull(postResponseAccountModel.getBody());


        postResponseAccountModel = restTemplate.postForEntity(getRootUrl() + "/api/accounts", accountModelB, AccountModel.class);
        assertNotNull(postResponseAccountModel);
        assertNotNull(postResponseAccountModel.getBody());

        TransactionFlowModel transactionFlowModelA = new TransactionFlowModel();
        transactionFlowModelA.setFromAccount(accountModelA);
        transactionFlowModelA.setToAccount(accountModelB);


        ResponseEntity<TransactionFlowModel> postResponsePaymentRealizedModel = restTemplate.postForEntity(getRootUrl() + "/api/transactionFlows", transactionFlowModelA, TransactionFlowModel.class);
        assertNotNull(postResponsePaymentRealizedModel);
        assertNotNull(postResponsePaymentRealizedModel.getBody());
    }
}
