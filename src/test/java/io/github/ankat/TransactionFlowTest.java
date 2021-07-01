package io.github.ankat;

import io.github.ankat.model.AccountModel;
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
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


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

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/api/accounts", HttpMethod.GET, entity, List.class);
        assertNotNull(response.getBody());

        List<AccountModel> accountModels = response.getBody();

        assertThat(accountModels, containsInAnyOrder(
                hasProperty("accountName", is("Account A")),
                hasProperty("accountName", is("Account B")),
                hasProperty("accountName", is("Account C"))
                )
        );


        TransactionFlowModel transactionFlowModelA = new TransactionFlowModel();
        transactionFlowModelA.setFromAccount(accountModels.get(0));
        transactionFlowModelA.setToAccount(accountModels.get(1));


        ResponseEntity<TransactionFlowModel> postResponsePaymentRealizedModel = restTemplate.postForEntity(getRootUrl() + "/api/transactionFlows", transactionFlowModelA, TransactionFlowModel.class);
        assertNotNull(postResponsePaymentRealizedModel);
        assertNotNull(postResponsePaymentRealizedModel.getBody());
    }
}
