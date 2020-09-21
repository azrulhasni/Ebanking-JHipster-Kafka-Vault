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

/**
 *
 * @author azrul
 */

//Added
public class Transaction {
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
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param ammount the amount to set
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
