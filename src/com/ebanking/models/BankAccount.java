package com.ebanking.models;

import com.ebanking.utils.BankAccountType;

public class BankAccount {
    private BankAccountType type;
    private double balance;
    private String holderName;
    private String holderSurname;
    private String IBAN;

    public BankAccount(BankAccountType type, String holderName, String holderSurname, String IBAN) {
        this.type = type;
        this.holderName = holderName;
        this.holderSurname = holderSurname;
        this.IBAN = IBAN;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderSurname() {
        return holderSurname;
    }

    public void setHolderSurname(String holderSurname) {
        this.holderSurname = holderSurname;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public BankAccountType getType() {
        return type;
    }

    public void setType(BankAccountType type) {
        this.type = type;
    }

    public void addAmmount(double ammount){
        this.balance += ammount;
    }

    public boolean subtractAmmount(double ammount){
        if(this.balance >= ammount){
            this.balance -= ammount;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Cont Bancar: " + type +
                ", Sold: " + balance + " lei" +
                ", Nume Detinator: '" + holderName + '\'' +
                ", Prenume Detinator:'" + holderSurname + '\'' +
                ", IBAN: '" + IBAN + '\'';
    }
}
