package com.example.ph.mobiliario.Lista_Compras;


public class ConstructorListaCompras {

    private String cmax;
    private String cmin;
    private String descripcion;
    private String existencia;
    private String nombre;
    private String tipo;
    private String umedida;


    public ConstructorListaCompras(String cmax, String cmin, String descripcion
            , String existencia, String nombre, String tipo, String umedida) {

        this.cmax = cmax;
        this.cmin = cmin;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.nombre = nombre;
        this.tipo = tipo;
        this.umedida = umedida;
    }

    public String getCmax() {
        return cmax;
    }
    public void setCmax(String cmax) {
        this.cmax = cmax;
    }


    public String getCmin() {
        return cmin;
    }
    public void setCmin(String cmin) {
        this.cmin = cmin;
    }


    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExistencia() {
        return existencia;
    }
    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUmedida() {
        return umedida;
    }
    public void setUmedida(String umedida) {
        this.umedida = umedida;
    }

}
