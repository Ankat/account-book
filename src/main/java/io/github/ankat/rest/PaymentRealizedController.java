package io.github.ankat.rest;

import io.github.ankat.exception.SomethingWentWrongException;
import io.github.ankat.model.AccountDetail;
import io.github.ankat.model.AccountModel;
import io.github.ankat.model.PaymentRealizedModel;
import io.github.ankat.service.AccountService;
import io.github.ankat.service.PaymentRealizedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment-realizeds")
public class PaymentRealizedController {

    private final PaymentRealizedService paymentRealizedService;
    private final AccountService accountService;

    @GetMapping("/{accountNo}")
    public ResponseEntity<List<PaymentRealizedModel>> getPaymentRealizedByAccountNo(@PathVariable String accountNo) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentRealizedService.getPaymentRealizedByAccountNo(accountNo));
    }

    @PostMapping
    public ResponseEntity<PaymentRealizedModel> savePaymentRealized(@RequestBody PaymentRealizedModel paymentRealizedModel) {
        Optional<PaymentRealizedModel> optionalPaymentRealizedModel = paymentRealizedService.savePaymentRealized(paymentRealizedModel);
        if (optionalPaymentRealizedModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalPaymentRealizedModel.get());
        } else {
            throw new SomethingWentWrongException("Something went wrong");
        }
    }

    @PostMapping("/{accountNo}")
    public ResponseEntity<Map<String, AccountDetail>> startPaymentRealized(@PathVariable("accountNo") String accountNo) {
        Optional<AccountModel> optionalFromAccountModel = accountService.getAccountByAccountNo(accountNo);
        if (optionalFromAccountModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(paymentRealizedService.startPaymentRealized(optionalFromAccountModel.get()));
        }
        throw new SomethingWentWrongException("Something went wrong");
    }
}
