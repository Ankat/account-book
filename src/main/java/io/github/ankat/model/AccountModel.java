package io.github.ankat.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountModel {

    private String accountNo;

    private String accountName;

    private Integer amount;
}
