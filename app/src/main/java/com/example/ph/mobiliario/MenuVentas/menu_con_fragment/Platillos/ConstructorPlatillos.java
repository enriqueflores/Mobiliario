package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos;

public class ConstructorPlatillos {
    private String clasificacion1;
    private String descripcion;
    private String destino;
    private String exixtencia;
    private String idPlatillo;
    private String nombre;
    private String precio;
    private String tipo;

    public ConstructorPlatillos(String nombre, String exixtencia, String clasificacion1, String descripcion, String precio, String tipo, String idPlatillo, String destino) {
        this.nombre = nombre;
        this.exixtencia = exixtencia;
        this.clasificacion1 = clasificacion1;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.idPlatillo = idPlatillo;
        this.destino = destino;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExixtencia() {
        return this.exixtencia;
    }

    public void setExixtencia(String exixtencia) {
        this.exixtencia = exixtencia;
    }

    public String getClasificacion1() {
        return this.clasificacion1;
    }

    public void setClasificacion1(String clasificacion1) {
        this.clasificacion1 = clasificacion1;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return this.precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDestino() {
        return this.destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getIdPlatillo() {
        return this.idPlatillo;
    }

    public void setIdPlatillo(String idPlatillo) {
        this.idPlatillo = idPlatillo;
    }
}
