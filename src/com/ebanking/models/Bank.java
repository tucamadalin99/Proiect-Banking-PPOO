package com.ebanking.models;

import com.ebanking.utils.UninitializedCollectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bank {
    private String name;
    private String address;
    private HashMap<String, Double> accountTypesStash;
    private List<User> users;
    private static Bank instance;

    private Bank(String name, String address) {
        this.name = name;
        this.address = address;
        this.accountTypesStash = new HashMap<>();
        this.users = new ArrayList<>();
    }

    synchronized public static Bank getInstance(String name, String address){
        instance = instance == null ? new Bank(name, address) : instance;
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setMoney(Double credit, Double debit, Double economy) throws UninitializedCollectionException {
        if(this.accountTypesStash != null){
            this.accountTypesStash.put("Credit", credit);
            this.accountTypesStash.put("Debit", debit);
            this.accountTypesStash.put("Economii", economy);
            this.accountTypesStash.put("Total", credit + debit + economy);
        }else{
            throw new UninitializedCollectionException("Colectia HashMap nu a fost instantiata");
        }
    }

    public void addUsers(User user){
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(user);
    }

    public HashMap<String, Double> getAccountTypesStash() {
        return accountTypesStash;
    }

    public void setAccountTypesStash(HashMap<String, Double> accountTypesStash) {
        this.accountTypesStash = accountTypesStash;
    }

    public User searchUser(String email, String password){
        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public BankAccount searchUserBankAccount(String email, String IBAN){
        for(User user : users){
            if(user.getEmail().equals(email)){
                for(BankAccount acc : user.getAccounts()){
                    if(acc.getIBAN().equals(IBAN)){
                        return acc;
                    }
                    return null;
                }
            }
        }
        return null;
    }
}