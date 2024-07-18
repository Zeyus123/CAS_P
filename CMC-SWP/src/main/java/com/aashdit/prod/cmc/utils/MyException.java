package com.aashdit.prod.cmc.utils;

public class MyException extends Exception {

    private static final long serialVersionUID = 4704158464899308484L;

    public MyException(String s) {
        // Call constructor of parent Exception
        super(s);
    }
}