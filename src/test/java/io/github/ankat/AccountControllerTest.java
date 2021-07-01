package io.github.ankat;

import io.github.ankat.model.AccountModel;
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

import java.util.List;


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
        AccountModel accountModelA = new AccountModel();
        accountModelA.setAccountNo("1111");
        accountModelA.setAccountName("Account A");
        accountModelA.setAmount(123);

        ResponseEntity<AccountModel> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/accounts", accountModelA, AccountModel.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }
}
