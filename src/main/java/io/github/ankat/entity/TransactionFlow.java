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
@Table(name = "TRANSACTION_FLOW", uniqueConstraints = {@UniqueConstraint(columnNames = {"FROM_ACCOUNT_NO", "TO_ACCOUNT_NO"})})
public class TransactionFlow implements Serializable {

    @Id
    @Column(name = "TRANSACTION_FLOW_ID")
    private String transactionFlowId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FROM_ACCOUNT_NO", referencedColumnName = "ACCOUNT_NO")
    private Account fromAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TO_ACCOUNT_NO", referencedColumnName = "ACCOUNT_NO")
    private Account toAccount;

}
