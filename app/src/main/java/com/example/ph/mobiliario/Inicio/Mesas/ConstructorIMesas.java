package com.example.ph.mobiliario.Inicio.Mesas;

public class ConstructorIMesas {
    private String cantidad;
    private String icono;
    private String titulo;

    public ConstructorIMesas(String titulo, String cantidad, String icono) {
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.icono = icono;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getIcono() {
        return this.icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
