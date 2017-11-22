package com.example.ph.mobiliario.MenuVentas.menu_con_fragment;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias.fragmentoCategorias;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias.fragmentoCategorias.OnFragmentInteractionListener;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes.modif.modifi;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes.paquetes;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Sub.Ingr;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.fragmentoPlatillos;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras.fragmentoCompras;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Carta extends AppCompatActivity implements OnFragmentInteractionListener, fragmentoPlatillos.OnFragmentInteractionListener, fragmentoCompras.OnFragmentInteractionListener {
    public static String Frag;
    public static String Frame;
    public static Button btnfr1;
    public static Button btnfr2;
    public static Button btnfr3;
    public static String colorBtnDes = "#ffffff";
    public static String colorBtnSel = "#FFEB3B";
    public static String comtras = "no";
    public static FragmentManager fmIng;
    public static double total = 0.0d;
    public static Ingr tvIng;
    public static modifi tvModifi;
    public static paquetes tvPaquetes;
    String pidMesero;
    String pmesa;
    String pnombre;
    String pnombreMesero;
    String ptotal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_carta);
        btnfr1 = (Button) findViewById(R.id.btnFrag1);
        btnfr2 = (Button) findViewById(R.id.btnFrag2);
        btnfr3 = (Button) findViewById(R.id.btnFrag3);
        fragmentoCategorias fragmento1= new fragmentoCategorias();
        FragmentTransaction transicion = getSupportFragmentManager().beginTransaction();
        transicion.replace(R.id.contenedor, fragmento1);
        transicion.commit();
        btnfr1.setBackgroundResource(R.drawable.fondo_frag_select);
        btnfr2.setBackgroundResource(R.drawable.fondo_frag_des_sel);
        btnfr3.setBackgroundResource(R.drawable.fondo_frag_des_sel);
        btnfr1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentoCategorias fragmento1 = new fragmentoCategorias();
                FragmentTransaction transicion = Carta.this.getSupportFragmentManager().beginTransaction();
                transicion.replace(R.id.contenedor, fragmento1);
                transicion.commit();
                Carta.btnfr1.setBackgroundResource(R.drawable.fondo_frag_select);
                Carta.btnfr2.setBackgroundResource(R.drawable.fondo_frag_des_sel);
                Carta.btnfr3.setBackgroundResource(R.drawable.fondo_frag_des_sel);
            }
        });
        btnfr2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentoPlatillos fragmento2 = new fragmentoPlatillos();
                FragmentTransaction transicion2 = Carta.this.getSupportFragmentManager().beginTransaction();
                transicion2.replace(R.id.contenedor, fragmento2);
                transicion2.commit();
                Carta.btnfr1.setBackgroundResource(R.drawable.fondo_frag_des_sel);
                Carta.btnfr2.setBackgroundResource(R.drawable.fondo_frag_select);
                Carta.btnfr3.setBackgroundResource(R.drawable.fondo_frag_des_sel);
            }
        });
        btnfr3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentoCompras fragmento = new fragmentoCompras();
                FragmentTransaction transicion = Carta.this.getSupportFragmentManager().beginTransaction();
                transicion.replace(R.id.contenedor, fragmento);
                transicion.commit();
                Carta.btnfr1.setBackgroundResource(R.drawable.fondo_frag_des_sel);
                Carta.btnfr2.setBackgroundResource(R.drawable.fondo_frag_des_sel);
                Carta.btnfr3.setBackgroundResource(R.drawable.fondo_frag_select);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Mesa.nomMesa);
        fmIng = getFragmentManager();
        fragmentoCompras.f_Saber_Precio1();
    }

    public static void f_Lanzar_Cambio_Ingredientes(String selIdClasificacion, String tipo, String nombre, String precio, String SelIdPlatillo, String SelDestino, String modificar, String contador, String selprecio, String selumedida) {
        if (modificar.equals("")) {
            if (SelDestino.equals("null")) {
                if (SelDestino.equals("null")) {
                    tvPaquetes = new paquetes(selIdClasificacion, tipo, nombre, precio, SelIdPlatillo, SelDestino);
                    tvPaquetes.show(fmIng, "TV");
                }
            } else {
                tvIng = new Ingr(selIdClasificacion, tipo, nombre, precio, SelIdPlatillo, SelDestino);
                tvIng.show(fmIng, "TV");
            }
        }
        if (modificar.equals("modificar")) {
            tvModifi = new modifi(selIdClasificacion, tipo, nombre, precio, SelIdPlatillo, SelDestino, contador, selprecio, selumedida);
            tvModifi.show(fmIng, "TV");
        }
    }

    protected void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (String.valueOf(dataSnapshot.exists()).equals("false")) {
                    Mesa.f_estus_mesa("Libre");
                }
                String a = "1";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onFragmentInteraction(Uri uri) {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_carta, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

