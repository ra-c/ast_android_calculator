package com.example.calculator;
public class Token {

    public String type;
    public double value;

    public Token(String type){
        this.type = type;
    }

    public Token(double value){
        this.value = value;
    }

    public boolean isNumber(){
        return type == null;
    }
}
