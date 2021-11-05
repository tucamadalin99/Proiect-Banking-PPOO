package com.ebanking.models;

import com.ebanking.utils.BankAccountType;
import com.ebanking.utils.UninitializedCollectionException;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static int counter = 0;
    private int uID;
    private String phone;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String CNP;
    private List<BankAccount> accounts;

    public User(String phone, String email, String password, String name, String surname, String CNP) {
        this.uID = ++User.counter;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.CNP = CNP;
        this.accounts = new ArrayList<>();
    }

    public int getuID() {
        return uID;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCNP() {
        return CNP;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public BankAccount getAccount(BankAccountType type){
        if(this.accounts != null){
            for(BankAccount acc : accounts){
                if(acc.getType() == type){
                    return acc;
                }
            }
            return null;
        }
        return null;
    }

    public void addAccount(BankAccount newAccount) throws UninitializedCollectionException {
        if(this.accounts == null){
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(newAccount);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return "{" +
                "uID:" + uID +
                ", phone:'" + phone + '\'' +
                ", email:'" + email + '\'' +
                ", name:'" + name + '\'' +
                ", surname:'" + surname + '\'' +
                ", CNP:'" + CNP + '\'' +
                ", accounts:" + accounts +
                '}';
    }
}
