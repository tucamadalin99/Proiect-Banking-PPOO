package com.ebanking.utils;
import com.ebanking.models.Bank;
import com.ebanking.models.User;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONUtils {
    public static void writeToJSON(List<User> users){
        try {
            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get("bank.json"));
            gson.toJson(users, writer);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readFromJSON(Bank bank) {
        try{
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("bank.json"));
            List<User> users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            bank.setUsers(users);
            System.out.println(bank.getUsers());
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
