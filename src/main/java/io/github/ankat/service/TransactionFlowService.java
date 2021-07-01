package io.github.ankat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ankat.entity.Account;
import io.github.ankat.entity.TransactionFlow;
import io.github.ankat.model.AccountModel;
import io.github.ankat.model.TransactionFlowModel;
import io.github.ankat.repository.TransactionFlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionFlowService {

    private final TransactionFlowRepository transactionFlowRepository;
    private final ObjectMapper objectMapper;

    public List<TransactionFlowModel> getTransactionFlows() {
        List<TransactionFlowModel> transactionFlowModels = objectMapper.convertValue(transactionFlowRepository.findAll(), List.class);
        return transactionFlowModels;
    }

    public Optional<TransactionFlowModel> getTransactionFlowByFromAccount(AccountModel fromAccountModel) {
        Optional<TransactionFlow> optionalTransactionFlow = transactionFlowRepository.findByFromAccountAccountNo(fromAccountModel.getAccountNo());
        if (optionalTransactionFlow.isPresent()) {
            return Optional.of(objectMapper.convertValue(optionalTransactionFlow.get(), TransactionFlowModel.class));
        }
        return Optional.empty();
    }

    public Optional<TransactionFlowModel> saveTransactionFlow(TransactionFlowModel transactionFlowModel) {
        AccountModel fromAccountModel = transactionFlowModel.getFromAccount();
        AccountModel toAccountModel = transactionFlowModel.getToAccount();

        TransactionFlow transactionFlow = new TransactionFlow();
        transactionFlow.setTransactionFlowId(UUID.randomUUID().toString());
        transactionFlow.setFromAccount(objectMapper.convertValue(fromAccountModel, Account.class));
        transactionFlow.setToAccount(objectMapper.convertValue(toAccountModel, Account.class));
        if (!validateTransactionFlow(objectMapper.convertValue(transactionFlow, TransactionFlowModel.class))) {
            transactionFlow = transactionFlowRepository.save(transactionFlow);
            return Optional.of(objectMapper.convertValue(transactionFlow, TransactionFlowModel.class));
        }
        return Optional.empty();
    }

    public Boolean validateTransactionFlow(TransactionFlowModel transactionFlowModel) {
        Optional<TransactionFlow> optionalTransactionFlow = transactionFlowRepository.findByFromAccountAccountNoAndToAccountAccountNo(transactionFlowModel.getFromAccount().getAccountNo(), transactionFlowModel.getToAccount().getAccountNo());
        if (optionalTransactionFlow.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
