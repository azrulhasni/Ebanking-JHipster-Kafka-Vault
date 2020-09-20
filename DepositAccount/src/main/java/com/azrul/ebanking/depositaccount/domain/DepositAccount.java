package com.azrul.ebanking.depositaccount.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A DepositAccount.
 */
@Entity
@Table(name = "deposit_account")
public class DepositAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "opening_date")
    private ZonedDateTime openingDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @Column(name = "last_transaction_date")
    private ZonedDateTime lastTransactionDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public DepositAccount accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getProductId() {
        return productId;
    }

    public DepositAccount productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZonedDateTime getOpeningDate() {
        return openingDate;
    }

    public DepositAccount openingDate(ZonedDateTime openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public void setOpeningDate(ZonedDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public Integer getStatus() {
        return status;
    }

    public DepositAccount status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public DepositAccount balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ZonedDateTime getLastTransactionDate() {
        return lastTransactionDate;
    }

    public DepositAccount lastTransactionDate(ZonedDateTime lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
        return this;
    }

    public void setLastTransactionDate(ZonedDateTime lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepositAccount)) {
            return false;
        }
        return id != null && id.equals(((DepositAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepositAccount{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", productId='" + getProductId() + "'" +
            ", openingDate='" + getOpeningDate() + "'" +
            ", status=" + getStatus() +
            ", balance=" + getBalance() +
            ", lastTransactionDate='" + getLastTransactionDate() + "'" +
            "}";
    }
}
