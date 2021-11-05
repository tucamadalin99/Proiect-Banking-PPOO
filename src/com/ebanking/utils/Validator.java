package com.ebanking.utils;

public class Validator {

    public static boolean validateAuth(String email, String password){
        if(email.length() == 0){
            System.out.println("Emailul nu a fost introdus");
            return false;
        }
        else if(!email.contains("@gmail.com")){
            System.out.println("Emailul trebuie sa contina @gmail.com");
            return false;
        }
        else if(password.length() == 0){
            System.out.println("Parola nu a fost introdusa");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean validateRegister(String phone, String email, String name, String surname, String CNP){
        if(phone.length() < 10){
            System.out.println("Numarul de telefon trebuie sa aiba 10 caractere");
            return false;
        }
        else if(!email.contains("@gmail.com")){
            System.out.println("Emailul trebuie sa contina @gmail.com");
            return false;
        }
        else if(name.length() == 0){
            System.out.println("Nu ati introdus numele");
            return false;
        }
        else if(surname.length() == 0){
            System.out.println("Nu ati introdus prenumele");
            return false;
        }
        else if(CNP.length() < 13){
            System.out.println("CNP-ul trebuie sa aiba 13 caractere");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean validMandatory(String input){
        return input.length() > 0;
    }

    public static boolean validEmail(String email){
        return email.contains("@gmail.com");
    }
}
