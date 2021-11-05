package com.ebanking.utils;

public class UninitializedCollectionException extends Exception{
    public UninitializedCollectionException(String error){
        super(error);
    }
}
