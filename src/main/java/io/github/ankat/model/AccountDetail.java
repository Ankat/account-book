package io.github.ankat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDetail {

    private Integer amount;
    private List<PaymentRealizedModel> paymentRealizedModels;
}
