package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras;

public class ConstructorCompras {
    private String accion;
    private String clasificacionMenu;
    private String clasificacionRecurso;
    private String codigoBarras;
    private String comentario;
    private String destino;
    private String estatus;
    private String id;
    private String nombre;
    private String precio;
    private String tipo;

    public ConstructorCompras(String id, String clasificacionMenu, String clasificacionRecurso, String codigoBarras, String estatus, String nombre, String precio, String tipo, String destino, String accion, String comentario) {
        this.id = id;
        this.clasificacionMenu = clasificacionMenu;
        this.clasificacionRecurso = clasificacionRecurso;
        this.codigoBarras = codigoBarras;
        this.estatus = estatus;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.destino = destino;
        this.accion = accion;
        this.comentario = comentario;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClasificacionMenu() {
        return this.clasificacionMenu;
    }

    public void setClasificacionMenu(String clasificacionMenu) {
        this.clasificacionMenu = clasificacionMenu;
    }

    public String getClasificacionRecurso() {
        return this.clasificacionRecurso;
    }

    public void setClasificacionRecurso(String clasificacionRecurso) {
        this.clasificacionRecurso = clasificacionRecurso;
    }

    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getEstatus() {
        return this.estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getAccion() {
        return this.accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}