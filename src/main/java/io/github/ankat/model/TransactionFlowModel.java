package io.github.ankat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionFlowModel {

    private String transactionFlowId;

    private AccountModel fromAccount;

    private AccountModel toAccount;
}
