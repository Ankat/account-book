package io.github.ankat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    @Id
    @Column(name = "ACCOUNT_NO", insertable = false, updatable = false, unique = true, nullable = false)
    private String accountNo;

    @Column(name = "ACCOUNT_NAME", nullable = false)
    private String accountName;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount = 0;

}
