package com.example.ph.mobiliario.Mesas;


public class ConstructorMesa {

    private String name;
    private String tabla;
    private String icono;


    public ConstructorMesa(String name, String tabla, String icono) {

        this.name = name;
        this.tabla = tabla;
        this.icono = icono;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }


    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }


}
