package com.ebanking.models;

import com.ebanking.utils.BankAccountType;
import com.ebanking.utils.UninitializedCollectionException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    public void setTotalStash(){
        if(this.accountTypesStash == null){
            this.accountTypesStash = new HashMap<>();
        }
        double totalCredit = getAllMoneyByType(BankAccountType.Credit);
        double totalDebit = getAllMoneyByType(BankAccountType.Debit);
        double totalEconomy = getAllMoneyByType(BankAccountType.Economy);
        double total = totalCredit + totalDebit + totalEconomy;
        this.accountTypesStash.put("credit", totalCredit);
        this.accountTypesStash.put("debit", totalDebit);
        this.accountTypesStash.put("economii", totalEconomy);
        this.accountTypesStash.put("total", total);
    }

    public double getAllMoneyByType(BankAccountType type){
        double total = 0.0;
        for(User user : this.users){
            if(user.getAccounts() != null)
            for(BankAccount acc : user.getAccounts()){
                if(acc.getType() == type)
                total += acc.getBalance();
            }
        }
        return total;
    }

    public void generateFileReport(){
        try{
            File file = new File("bankReport.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("--- Suma totala a banilor depusi in banca pe categorii ---\n");
            bw.write("Total Credit depus: " + this.accountTypesStash.get("credit") + " RON" + "\n");
            bw.write("Total Debit depus: " + this.accountTypesStash.get("debit") + " RON" + "\n");
            bw.write("Total Economii depuse: " + this.accountTypesStash.get("economii") + " RON" + "\n");
            bw.write("Total tot: " + this.accountTypesStash.get("total") + " RON" + "\n");
            bw.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
