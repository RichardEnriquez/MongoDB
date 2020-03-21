package com.kiwi.Modelo;

public class RankingTO {
    private Empleado empleado;
    private int cantidadIncidencias;

    public RankingTO(Empleado empleado, int cantidadIncidencias) {
        this.empleado = empleado;
        this.cantidadIncidencias = cantidadIncidencias;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getCantidadIncidencias() {
        return cantidadIncidencias;
    }

    public void setCantidadIncidencias(int cantidadIncidencias) {
        this.cantidadIncidencias = cantidadIncidencias;
    }

    @Override
    public String toString() {
        return "RankingTO{" +
                "empleado=" + empleado +
                ", cantidadIncidencias=" + cantidadIncidencias +
                '}';
    }

}
