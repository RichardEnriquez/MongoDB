package com.kiwi.excepciones;

public class Excepciones extends Exception{
    static String[] excepcion = new String[]{
            "+1",
            "< ERROR 001: Username o Password incorrecto >\n",
            "< ERROR 002: Las contraseñas no coinciden >\n",
            "< ERROR 003: Opcion desconocida >\n",
            "< ERROR 004: El username es igual que el original >\n",
            "< ERROR 005: Password incorrecto >\n",
            "< ERROR 006: No dispones de incidencias >\n",
            "< ERROR 007: No se encontraron resultados >\n",
            "< ERROR 008: Destino incorrecto >\n",
            "< ERROR 009: No dispone de incidencias >\n",
            "< ERROR 010: Este usuario aun no inicio session >\n",

    };

    public Excepciones(int code){
        super(excepcion[code]);
    }

}
