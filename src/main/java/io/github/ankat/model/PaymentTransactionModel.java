package io.github.ankat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTransactionModel {

    private Integer paymentId;

    private AccountModel account;

    private Integer debitAmount = 0;

    private Integer creditAmount = 0;

    private Integer openingBalance = 0;

    private Integer closingBalance = 0;

}
