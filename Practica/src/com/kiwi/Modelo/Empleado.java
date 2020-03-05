package com.kiwi.Modelo;

public class Empleado {
    private String nombreUsu;
    private String pass;
    private String nombre;
    private int telefono;

    public Empleado(String nombreUsu, String pass, String nombre, int telefono) {
        this.nombreUsu = nombreUsu;
        this.pass = pass;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
