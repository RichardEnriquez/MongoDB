package com.kiwi.Modelo;

public class Empleado {
    private String _ID = null;
    private String username;
    private String pass;
    private String nombre;
    private int telefono;

    public Empleado(String username, String pass, String nombre, int telefono) {
        this.username = username;
        this.pass = pass;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nombreUsu) {
        this.username = nombreUsu;
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

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                " username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
