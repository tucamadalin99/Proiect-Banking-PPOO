package com.ebanking.utils;

import com.ebanking.models.Bank;
import java.util.Scanner;

import com.ebanking.models.BankAccount;
import com.ebanking.models.User;

public class Menu {

    public static void initMenu() {
        Bank bank = Bank.getInstance("Madalin Banking",
                "Str.Sudului, nr.8, Cartier Berceni, Bucuresti"
        );
        try {
            bank.setMoney(0.0, 0.0, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONUtils.readFromJSON(bank);
        bank.setTotalStash();
        User.counter = bank.getUsers() != null ? bank.getUsers().size() : 0;
        System.out.println("Bine ati venit la aplicatia de e-banking " + bank.getName());
        System.out.println("--------------------------------------------------------");
        Scanner input = new Scanner(System.in);
        int option = 1;
        while(option != 0) {
            System.out.println("Apasati 1 pentru creare cont");
            System.out.println("Apasati 2 pentru autentificare");
            System.out.println("Apasati 0 pentru inchidere aplicatie");
            option = input.nextInt();
            boolean validData = false;
            switch (option) {
                case 1:
                    while (!validData) {
                        System.out.println("Introduceti numar de telefon: ");
                        String phone = input.next();
                        System.out.println("Introduceti email: ");
                        String emailAddr = input.next();
                        System.out.println("Introduceti parola: ");
                        String pass = input.next();
                        System.out.println("Introduceti nume: ");
                        String name = input.next();
                        System.out.println("Introduceti prenume: ");
                        String surname = input.next();
                        System.out.println("Introduceti CNP: ");
                        String CNP = input.next();
                        validData = Validator.validateRegister(phone, emailAddr, name, surname, CNP);
                        User newUser = validData ? new User(phone, emailAddr,pass, name, surname, CNP) : null;
                        if(newUser != null){
                            bank.addUsers(newUser);
                            System.out.println("Utilizator inregistrat cu succes!");
                        }
                    }
                    break;
                case 2:
                    String email;
                    String password;
                    while (!validData) {
                        System.out.println("-- Autentificare --");
                        System.out.println("Email: ");
                        email = input.next();
                        System.out.println("Parola: ");
                        password = input.next();
                        validData = Validator.validateAuth(email, password);
                        User authUser = null;
                        if(bank.getUsers() != null){
                            authUser = validData ? bank.searchUser(email, password) : null;
                        }else{
                            System.out.println("Nu exista conturi inregistrate la aceasta banca.");
                        }
                        if(authUser != null){
                            System.out.println("----------------------------------------------------");
                            System.out.println("Bine ati venit in contul dvs. " + authUser.getName() + " " + authUser.getSurname());
                            int option2 = 0;
                            while (option2 != 6){
                                System.out.println("----------------------------------------------------");
                                System.out.println("1. Interogare sold");
                                System.out.println("2. Depunere numerar");
                                System.out.println("3. Adaugare numerar");
                                System.out.println("4. Adaugare cont bancar");
                                System.out.println("5. Anulare cont bancar");
                                System.out.println("6. Reveniti la meniul anterior");
                                System.out.println("------------------------------");
                                option2 = input.nextInt();
                                BankAccountType bankEnum = BankAccountType.Credit;
                                switch (option2) {
                                    case 1 -> {
                                        System.out.println("Introduceti tipul de cont(Credit, Debit, Economii): ");
                                        String accType = input.next();
                                        switch (accType) {
                                            case "credit" -> bankEnum = BankAccountType.Credit;
                                            case "debit" -> bankEnum = BankAccountType.Debit;
                                            case "economii" -> bankEnum = BankAccountType.Economy;
                                            default -> System.out.println("Tip cont invalid! A fost aplicat tipul implicit Credit.");
                                        }
                                        BankAccount foundAccount = authUser.getAccount(bankEnum);
                                        if (foundAccount != null) {
                                            System.out.println(foundAccount);
                                        } else {
                                            System.out.println("----------------------------");
                                            System.out.println("Eroare: Cont de acest tip inexistent");
                                            System.out.println("----------------------------");
                                        }
                                    }
                                    case 2 -> {
                                        System.out.println("Introduceti email-ul utilizatorului: ");
                                        String findEmail = input.next();
                                        System.out.println("Introduceti IBAN-ul contului in care transferati: ");
                                        String findIBAN = input.next();
                                        System.out.println("Introduceti tipul contului dvs. din care doriti sa efectuati transferul: ");
                                        String accTypeStr = input.next();
                                        BankAccountType typeEnum = BankAccountType.Credit;
                                        switch (accTypeStr) {
                                            case "credit" -> typeEnum = BankAccountType.Credit;
                                            case "debit" -> typeEnum = BankAccountType.Debit;
                                            case "economii" -> typeEnum = BankAccountType.Economy;
                                            default -> System.out.println("Tip invalid. Se efectueaza transferul din contul implicit de Credit");
                                        }
                                        BankAccount currentAcc = authUser.getAccount(typeEnum);
                                        if (currentAcc != null) {
                                            System.out.println("Introduceti suma transferata: ");
                                            double ammount = input.nextDouble();
                                            BankAccount foundAcc = bank.searchUserBankAccount(findEmail, findIBAN);
                                            if (foundAcc != null) {
                                                boolean transferMade = currentAcc.subtractAmmount(ammount);
                                                if (transferMade) {
                                                    foundAcc.addAmmount(ammount);
                                                    System.out.println("----------------------------");
                                                    System.out.println("Suma transferata cu success.");
                                                    System.out.println("Suma transferata: " + ammount);
                                                    System.out.println("Sold ramas: " + currentAcc.getBalance() + " lei");
                                                    System.out.println("----------------------------");
                                                } else {
                                                    System.out.println("Contul dvs. nu are suficienti bani pt aceasta suma");
                                                }
                                            } else {
                                                System.out.println("Contul utilizatorului nu a fost gasit.");
                                            }
                                        } else {
                                            System.out.println("Contul nu a fost gasit.");
                                        }
                                    }
                                    case 3 -> {
                                        System.out.println("Introduceti tipul de cont in care doriti sa incarcati bani: ");
                                        String addType = input.next();
                                        System.out.println("Introduceti suma pe care doriti sa o adaugati: ");
                                        double sum = input.nextDouble();
                                        BankAccountType addTypeEnum = BankAccountType.Credit;
                                        switch (addType) {
                                            case "credit" -> addTypeEnum = BankAccountType.Credit;
                                            case "debit" -> addTypeEnum = BankAccountType.Debit;
                                            case "economii" -> addTypeEnum = BankAccountType.Economy;
                                            default -> System.out.println("Tip invalid. Se efectueaza transferul din contul implicit de Credit");
                                        }
                                        BankAccount foundAcc = authUser.getAccount(addTypeEnum);
                                        if (foundAcc == null) {
                                            System.out.println("Contul de acest tip nu exista");
                                        } else {
                                            foundAcc.addAmmount(sum);
                                            System.out.println("Transfer efectuat!");
                                        }
                                    }
                                    case 4 -> {
                                        System.out.println("Tip cont(Credit, Debit, Economii): ");
                                        String type = input.next();
                                        switch (type) {
                                            case "credit" -> bankEnum = BankAccountType.Credit;
                                            case "debit" -> bankEnum = BankAccountType.Debit;
                                            case "economii" -> bankEnum = BankAccountType.Economy;
                                            default -> System.out.println("Tip cont invalid! A fost aplicat tipul implicit Credit.");
                                        }
                                        System.out.println("Nume: ");
                                        String name = input.next();
                                        System.out.println("Prenume: ");
                                        String surname = input.next();
                                        System.out.println("IBAN: ");
                                        String IBAN = input.next();
                                        try {
                                            authUser.addAccount(new BankAccount(bankEnum, name, surname, IBAN));
                                        } catch (UninitializedCollectionException e) {
                                            e.printStackTrace();
                                        }
                                        System.out.println("Cont adaugat cu succes in aplicatie...");
                                    }
                                    case 5 -> {
                                        System.out.println("Introduceti tipul de cont pe care doriti sa il anulati: ");
                                        String removeAccType = input.next();
                                        BankAccountType removeAccEnum = BankAccountType.Credit;
                                        switch (removeAccType) {
                                            case "credit" -> removeAccEnum = BankAccountType.Credit;
                                            case "debit" -> removeAccEnum = BankAccountType.Debit;
                                            case "economii" -> removeAccEnum = BankAccountType.Economy;
                                            default -> System.out.println("Tip cont invalid!.");
                                        }
                                        BankAccount removingAccount = authUser.getAccount(removeAccEnum);
                                        if (removingAccount != null) {
                                            authUser.getAccounts().remove(removingAccount);
                                            System.out.println("Contu anulat..");
                                        } else {
                                            System.out.println("Contul nu exista, nu poate fi anulat.");
                                        }
                                    }
                                    default -> {
                                    }
                                }
                            }
                        }else{
                            System.out.println("Credentiale gresite/user inexistent!");
                        }
                    }
                    break;
                default:
                    System.out.println();
            }
        }
        input.close();
        System.out.println("--- Datele au fost actualizate in fisier ----");
        JSONUtils.writeToJSON(bank.getUsers());
        System.out.println("--- Un raport financiar a fost creat --");
        bank.generateFileReport();
    }

}
