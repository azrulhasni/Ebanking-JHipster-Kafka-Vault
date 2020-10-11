/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azrul.ebanking.common.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author azrul
 */

//Added
public class Transaction implements Serializable{
    
    public Transaction(){}

    @Override
    public String toString() {
        return "Transaction{" + "fromAccountNumber=" + fromAccountNumber + ", toAccountNumber=" + toAccountNumber + ", amount=" + amount + ", finalBalance=" + finalBalance + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.fromAccountNumber);
        hash = 37 * hash + Objects.hashCode(this.toAccountNumber);
        hash = 37 * hash + Objects.hashCode(this.amount);
        hash = 37 * hash + Objects.hashCode(this.finalBalance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.fromAccountNumber, other.fromAccountNumber)) {
            return false;
        }
        if (!Objects.equals(this.toAccountNumber, other.toAccountNumber)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.finalBalance, other.finalBalance)) {
            return false;
        }
        return true;
    }
    private String fromAccountNumber;
    private String toAccountNumber;
    private String amount;
    private String finalBalance;

    /**
     * @return the fromAccountNumber
     */
    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    /**
     * @param fromAccountNumber the fromAccountNumber to set
     */
    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    /**
     * @return the toAccountNumber
     */
    public String getToAccountNumber() {
        return toAccountNumber;
    }

    /**
     * @param toAccountNumber the toAccountNumber to set
     */
    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    /**
     * @return the ammount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param ammount the ammount to set
     */
    public void setAmount(String ammount) {
        this.amount = ammount;
    }

    /**
     * @return the finalBalance
     */
    public String getFinalBalance() {
        return finalBalance;
    }

    /**
     * @param finalBalance the finalBalance to set
     */
    public void setFinalBalance(String finalBalance) {
        this.finalBalance = finalBalance;
    }
}
