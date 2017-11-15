package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias;

public class ConstructorCategorias {
    private String icono;
    private String name;
    private String tabla;

    public ConstructorCategorias(String name, String tabla, String icono) {
        this.name = name;
        this.tabla = tabla;
        this.icono = icono;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getIcono() {
        return this.icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
