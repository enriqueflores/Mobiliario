package com.example.ph.mobiliario.Inicio.tikets;


public class ConstructorITikets {

    private String titulo;
    private String cantidad;
    private String icono;


    public ConstructorITikets(String titulo, String cantidad, String icono) {

        this.titulo = titulo;
        this.cantidad = cantidad;
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }


}
