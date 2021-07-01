package io.github.ankat.rest;

import io.github.ankat.exception.SomethingWentWrongException;
import io.github.ankat.model.TransactionFlowModel;
import io.github.ankat.service.TransactionFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction-flows")
public class TransactionFlowController {

    private final TransactionFlowService transactionFlowService;

    @PostMapping
    public ResponseEntity<TransactionFlowModel> saveTransactionFlow(@RequestBody TransactionFlowModel transactionFlowModel) {
        Optional<TransactionFlowModel> optionalTransactionFlowModel = transactionFlowService.saveTransactionFlow(transactionFlowModel);
        if (optionalTransactionFlowModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalTransactionFlowModel.get());
        } else {
            throw new SomethingWentWrongException("Duplicate transaction flow not allowed");
        }
    }

    @GetMapping
    public ResponseEntity<List<TransactionFlowModel>> getTransactionFlows() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionFlowService.getTransactionFlows());
    }

    @PostMapping("/validateTransactionFlow")
    public ResponseEntity<Boolean> validateTransactionFlow(@RequestBody TransactionFlowModel transactionFlowModel) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionFlowService.validateTransactionFlow(transactionFlowModel));


        /*
        String accountValue = accountFlowMap.get(flowRequestBody.getFromAccount().getAccountNo());
        if (accountValue == null) {
            return ResponseEntity.status(HttpStatus.OK).body("INVALID");
        } else {
            if (accountValue.equals(flowRequestBody.getToAccount().getAccountNo())) {
                return ResponseEntity.status(HttpStatus.OK).body("VALID");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("INVALID");
            }
        }
        */
        //return ResponseEntity.status(HttpStatus.OK).body(accountFlowMap.computeIfPresent(flowRequestBody.getFromAccount().getAccountNo(), (s1, s2) -> (s1.equals(s2) ? "VALID" : "INVALID")));
    }
}
