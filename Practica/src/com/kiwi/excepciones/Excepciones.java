package com.kiwi.excepciones;

public class Excepciones extends Exception{
    static String[] excepcion = new String[]{
            "+1",
            "< ERROR 001: Username o Password incorrecto >",

    };

    public Excepciones(int code){
        super(excepcion[code]);
    }

}
