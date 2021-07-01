package io.github.ankat.model;

import io.github.ankat.enums.PaymentRealizedStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRealizedModel {

    private Integer paymentRealizedId;

    private AccountModel account;

    private Integer realizedAmount;

    private PaymentRealizedStatus paymentRealizedStatus = PaymentRealizedStatus.PAYMENT_NOT_REALIZED;
}
