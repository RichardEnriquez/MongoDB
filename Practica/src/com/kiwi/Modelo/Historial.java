package com.kiwi.Modelo;

public class Historial {
    private String tipoEvent;
    private String fechaHora;
    private String nombreUsuario;

    public Historial(String tipoEvent, String fechaHora, String nombreUsuario) {
        this.tipoEvent = tipoEvent;
        this.fechaHora = fechaHora;
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoEvent() {
        return tipoEvent;
    }

    public void setTipoEvent(String tipoEvent) {
        this.tipoEvent = tipoEvent;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
