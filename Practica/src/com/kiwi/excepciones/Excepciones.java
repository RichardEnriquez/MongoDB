package com.kiwi.excepciones;

public class Excepciones extends Exception{
    static String[] excepcion = new String[]{
            "+1",
            "< ERROR 001: Username o Password incorrecto >\n",
            "< ERROR 002: Las contraseñas no coinciden >\n",
            "< ERROR 003: Opcion desconocida >\n",
            "< ERROR 004: El username es igual que el original >\n",


    };

    public Excepciones(int code){
        super(excepcion[code]);
    }

}
