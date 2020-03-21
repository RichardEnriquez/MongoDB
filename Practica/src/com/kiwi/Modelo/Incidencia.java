package com.kiwi.Modelo;

public class Incidencia {
    private String fechaHora;
    private String origen;
    private String destino;
    private String detalle;
    private String tipo;

    public Incidencia(String fechaHora, String origen, String destino, String detalle, String tipo) {
        this.fechaHora = fechaHora;
        this.origen = origen;
        this.destino = destino;
        this.detalle = detalle;
        this.tipo = tipo;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "fechaHora='" + fechaHora + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", detalle='" + detalle + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
