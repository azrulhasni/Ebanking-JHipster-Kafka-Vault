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
public class TransactionDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String ammount;
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
    public String getAmmount() {
        return ammount;
    }

    /**
     * @param ammount the ammount to set
     */
    public void setAmmount(String ammount) {
        this.ammount = ammount;
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
