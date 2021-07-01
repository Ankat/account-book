package io.github.ankat.rest;

import io.github.ankat.exception.NotFoundException;
import io.github.ankat.exception.SomethingWentWrongException;
import io.github.ankat.model.AccountModel;
import io.github.ankat.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountModel>> getAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccounts());
    }

    @GetMapping("/{accountNo}")
    public ResponseEntity<AccountModel> getAccountByAccountNo(@PathVariable("accountNo") String accountNo) {
        Optional<AccountModel> optionalAccount = accountService.getAccountByAccountNo(accountNo);
        if (optionalAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalAccount.get());
        } else {
            throw new NotFoundException("No Account Found");
        }
    }

    @PostMapping
    public ResponseEntity<AccountModel> saveAccount(@RequestBody AccountModel accountModel) {
        accountModel.setAccountNo(UUID.randomUUID().toString());
        Optional<AccountModel> optionalAccountModel = accountService.saveAccount(accountModel);
        if (optionalAccountModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalAccountModel.get());
        } else {
            throw new SomethingWentWrongException("Something went wrong");
        }
    }
}
