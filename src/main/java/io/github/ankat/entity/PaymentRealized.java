package io.github.ankat.entity;

import io.github.ankat.enums.PaymentRealizedStatus;
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
@Table(name = "PAYMENT_REALIZED")
public class PaymentRealized implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentRealizedId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ACCOUNT_NO")
    private Account account;

    @Column(name = "REALIZED_AMOUNT")
    private Integer realizedAmount;

    @Enumerated(EnumType.STRING)
    private PaymentRealizedStatus paymentRealizedStatus = PaymentRealizedStatus.PAYMENT_NOT_REALIZED;

}
