package com.azrul.ebanking.depositaccount.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.azrul.ebanking.depositaccount.domain.DepositAccount} entity.
 */
public class DepositAccountDTO implements Serializable {
    
    private Long id;

    private String accountNumber;

    private String productId;

    private ZonedDateTime openingDate;

    private Integer status;

    private BigDecimal balance;

    private ZonedDateTime lastTransactionDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZonedDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(ZonedDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ZonedDateTime getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(ZonedDateTime lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepositAccountDTO depositAccountDTO = (DepositAccountDTO) o;
        if (depositAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depositAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepositAccountDTO{" +
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
