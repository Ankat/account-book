package io.github.ankat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "PAYMENT_TRANSACTION")
public class PaymentTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ACCOUNT_NO")
    private Account account;

    @Column(name = "DEBIT_AMOUNT")
    private Integer debitAmount = 0;

    @Column(name = "CREDIT_AMOUNT")
    private Integer creditAmount = 0;

    @Column(name = "OPENING_BALANCE")
    private Integer openingBalance = 0;

    @Column(name = "CLOSING_BALANCE")
    private Integer closingBalance = 0;
}
