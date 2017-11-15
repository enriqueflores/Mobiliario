package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Sub;

public class ConstructorSub {
    private String cantidad;
    private String clasificacionIngredientes;
    private String contador;
    private String idIngre;
    private String nivel;
    private String nombreIngrediente;
    private String precioUnitario;
    private String tipo;
    private String uMedida;

    public ConstructorSub(String nombreIngrediente, String cantidad, String contador, String clasificacionIngredientes, String tipo, String uMedida, String idIngre, String precioUnitario, String nivel) {
        this.nombreIngrediente = nombreIngrediente;
        this.cantidad = cantidad;
        this.contador = contador;
        this.clasificacionIngredientes = clasificacionIngredientes;
        this.tipo = tipo;
        this.uMedida = uMedida;
        this.idIngre = idIngre;
        this.precioUnitario = precioUnitario;
        this.nivel = nivel;
    }

    public String getNombreIngrediente() {
        return this.nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public String getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNivel() {
        return this.nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getContador() {
        return this.contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getClasificacionIngredientes() {
        return this.clasificacionIngredientes;
    }

    public void setClasificacionIngredientes(String clasificacionIngredientes) {
        this.clasificacionIngredientes = clasificacionIngredientes;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String contador) {
        this.tipo = this.tipo;
    }

    public String getUMedida() {
        return this.uMedida;
    }

    public void setUMedida(String uMedida) {
        this.uMedida = uMedida;
    }

    public String getIdIngre() {
        return this.idIngre;
    }

    public void setIdIngre(String idIngre) {
        this.idIngre = idIngre;
    }

    public String getPrecioUnitario() {
        return this.precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
