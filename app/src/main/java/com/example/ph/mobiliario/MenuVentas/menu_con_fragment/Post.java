package com.example.ph.mobiliario.MenuVentas.menu_con_fragment;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public String idMesero;
    public String mesa;
    public String nombre;
    public String total;
    public String nombreMesero;
    public int starCount = 0;
    //public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String idMesero, String mesa, String nombre, String total,String nombreMesero) {
        this.idMesero = idMesero;
        this.nombre = nombre;
        this.total = total;
        this.mesa = mesa;
        this.nombreMesero = nombreMesero;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idMesero", idMesero);
        result.put("nombre", nombre);
        result.put("total", total);
        result.put("mesa", mesa);
        result.put("nombreMesero", nombreMesero);
        //result.put("stars", stars);

        return result;
    }

}