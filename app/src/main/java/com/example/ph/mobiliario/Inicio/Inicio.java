package com.example.ph.mobiliario.Inicio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ph.mobiliario.Inicio.tikets.AdaptadorITikets;
import com.example.ph.mobiliario.Inicio.tikets.ClickeadorITikets;
import com.example.ph.mobiliario.Inicio.tikets.ConstructorITikets;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.Adaptador;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Inicio.Comandas.AdaptadorIComandas;
import com.example.ph.mobiliario.Inicio.Comandas.ClickeadorIComandas;
import com.example.ph.mobiliario.Inicio.Comandas.ConstructorIComandas;
import com.example.ph.mobiliario.Inicio.Mesas.AdaptadorIMesas;
import com.example.ph.mobiliario.Inicio.Mesas.ClickeadorIMesas;
import com.example.ph.mobiliario.Inicio.Mesas.ConstructorIMesas;
import com.example.ph.mobiliario.Inicio.tikets.AdaptadorITikets;
import com.example.ph.mobiliario.Inicio.tikets.ClickeadorITikets;
import com.example.ph.mobiliario.Inicio.tikets.ConstructorITikets;
import com.example.ph.mobiliario.Login.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Inicio extends AppCompatActivity {
    private static List<ConstructorIComandas> colorsIComandas;
    private static List<ConstructorIMesas> colorsIMesas;
    private static List<ConstructorITikets> colorsITikets;
    static RecyclerView recyclerViewIComandas;
    static RecyclerView recyclerViewIMesas;
    static RecyclerView recyclerViewITikets;
    int bloqueadas;
    int cocinando;
    int entregadas;
    int enviado;
    int espera;
    int libres;
    int ocupadas;
    int pagando;
    int registradas;
    int resolviendo;
    int resuelto;

    class C07021 implements ValueEventListener {
        C07021() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Inicio.this.entregadas = 0;
            Inicio.this.cocinando = 0;
            Inicio.this.pagando = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                if (Inicio.this.F_TimeStamp_Fecha(postSnapshot.getKey()).equals(Inicio.f_Sacar_Fecha_Actual())) {
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    Inicio inicio;
                    if (estatus.equals("cocinando")) {
                        inicio = Inicio.this;
                        inicio.cocinando++;
                    } else if (estatus.equals("entregado")) {
                        inicio = Inicio.this;
                        inicio.entregadas++;
                    }
                }
            }
            Inicio.this.F_Consutar_porpagar();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07032 implements ValueEventListener {
        C07032() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                if (Inicio.this.F_TimeStamp_Fecha(String.valueOf(postSnapshot.getKey())).equals(Inicio.f_Sacar_Fecha_Actual())) {
                    Inicio inicio = Inicio.this;
                    inicio.pagando++;
                }
            }
            Inicio.this.f_Llenar_Recycler_Comandas();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07043 implements ValueEventListener {
        C07043() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Inicio.this.registradas = 0;
            Inicio.this.libres = 0;
            Inicio.this.ocupadas = 0;
            Inicio.this.bloqueadas = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String idtiket = postSnapshot.getKey();
                if (!(idtiket.equals("limiteReservacion") || idtiket.equals("periodoReservacion") || idtiket.equals("tiempoEspera"))) {
                    Inicio inicio = Inicio.this;
                    inicio.registradas++;
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    if (estatus.equals("Libre")) {
                        inicio = Inicio.this;
                        inicio.libres++;
                    } else if (estatus.equals("Ocupada")) {
                        inicio = Inicio.this;
                        inicio.ocupadas++;
                    } else if (estatus.equals("Bloqueada")) {
                        inicio = Inicio.this;
                        inicio.bloqueadas++;
                    }
                }
            }
            Inicio.this.f_Llenar_Recycler_Mesas(Inicio.this.libres, Inicio.this.ocupadas, Inicio.this.bloqueadas);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07054 implements ValueEventListener {
        C07054() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Inicio.this.enviado = 0;
            Inicio.this.espera = 0;
            Inicio.this.resolviendo = 0;
            Inicio.this.resuelto = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Inicio.this.F_Consutar_idtiket(postSnapshot.getKey());
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07065 implements ValueEventListener {
        C07065() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String idtiket = postSnapshot.getKey();
                String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                Inicio inicio;
                if (estatus.equals("Enviado")) {
                    inicio = Inicio.this;
                    inicio.enviado++;
                } else if (estatus.equals("Espera")) {
                    inicio = Inicio.this;
                    inicio.espera++;
                } else if (estatus.equals("Resolviendo")) {
                    inicio = Inicio.this;
                    inicio.resolviendo++;
                } else if (estatus.equals("Resuelto")) {
                    inicio = Inicio.this;
                    inicio.resuelto++;
                }
            }
            Inicio.this.f_Llenar_Recycler_Tikets(Inicio.this.enviado, Inicio.this.espera, Inicio.this.resolviendo, Inicio.this.resuelto);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07076 implements ClickeadorIComandas {
        C07076() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorIComandas) Inicio.colorsIComandas.get(position)).getCantidad());
        }
    }

    class C07087 implements ClickeadorIMesas {
        C07087() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorIMesas) Inicio.colorsIMesas.get(position)).getCantidad());
        }
    }

    class C07098 implements ClickeadorITikets {
        C07098() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorITikets) Inicio.colorsITikets.get(position)).getCantidad());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_inicio);
        getSupportActionBar().setTitle((CharSequence) "Inicio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colorsITikets = new ArrayList();
        colorsIMesas = new ArrayList();
        colorsIComandas = new ArrayList();
        F_Consutar_idMaste_Tikets();
        F_Consutar_idMaste_Mesas();
        F_Consutar_idMaste_Comandas();
    }

    public void F_fecha_TimeStap() {
        Date dateAux = null;
        try {
            dateAux = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("1/11/2017" + " " + "12:43:33");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = dateAux.getTime();
    }

    public String F_TimeStamp_Fecha(String id) {
        Date date = new Date(Long.parseLong(id));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        String formatted = format.format(date);
        System.out.println(formatted);
        return formatted;
    }

    public static String f_Sacar_Fecha_Actual() {
        Date d = new Date();
        String milisegundosparaKey = String.valueOf(d.getTime());
        String fechacComplString = new SimpleDateFormat("dd'/'MM'/'yyyy").format(d);
        Log.i("FECHA COMPLETA", fechacComplString);
        String horaString = new SimpleDateFormat("h:mm:ss a").format(d);
        Log.i("HORA", horaString);
        String sFrecha = fechacComplString + "  " + horaString;
        Log.i("dia con letras", new SimpleDateFormat("EEEE").format(d));
        Log.i("mes con letras", new SimpleDateFormat("MMMM").format(d));
        Log.i("Año", new SimpleDateFormat("yyyy").format(d));
        return fechacComplString;
    }

    private void F_Consutar_idMaste_Comandas() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").addValueEventListener(new C07021());
    }

    private void F_Consutar_porpagar() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("porpagar").addValueEventListener(new C07032());
    }

    private void F_Consutar_idMaste_Mesas() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("mesas").addValueEventListener(new C07043());
    }

    private void F_Consutar_idMaste_Tikets() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").addValueEventListener(new C07054());
    }

    private void F_Consutar_idtiket(String idMaster) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").child(idMaster).addListenerForSingleValueEvent(new C07065());
    }

    public void f_Llenar_Recycler_Comandas() {
        colorsIComandas.clear();
        String Entregadas = String.valueOf(this.entregadas);
        String Cocinando = String.valueOf(this.cocinando);
        String Pagando = String.valueOf(this.pagando);
        colorsIComandas.add(new ConstructorIComandas("Entregadas", Entregadas, ""));
        colorsIComandas.add(new ConstructorIComandas("Cocinando", Cocinando, ""));
        colorsIComandas.add(new ConstructorIComandas("Pagando", Pagando, ""));
        recyclerViewIComandas = (RecyclerView) findViewById(R.id.recyclerViewComandas);
        recyclerViewIComandas.clearOnScrollListeners();
        recyclerViewIComandas.clearOnChildAttachStateChangeListeners();
        recyclerViewIComandas.setAdapter(new AdaptadorIComandas(colorsIComandas, new C07076()));
        recyclerViewIComandas.setLayoutManager(new LinearLayoutManager(this));
    }

    public void f_Llenar_Recycler_Mesas(int libres, int ocupadas, int bloqueadas) {
        colorsIMesas.clear();
        String Libre = String.valueOf(libres);
        String Ocupadas = String.valueOf(ocupadas);
        String Bloqueadas = String.valueOf(bloqueadas);
        String Registradas = String.valueOf(this.registradas);
        String icono = ((char) ((int) Long.parseLong("f2c1".replace("&#x", "").replace(";", ""), 16))) + "";

        colorsIMesas.add(new ConstructorIMesas("Libres", Libre,icono));
        colorsIMesas.add(new ConstructorIMesas("Ocupadas", Ocupadas, "hola"));
        colorsIMesas.add(new ConstructorIMesas("Bloqueadas", Bloqueadas, ""));
        colorsIMesas.add(new ConstructorIMesas("Registradas", Registradas, ""));
        recyclerViewIMesas = (RecyclerView) findViewById(R.id.recyclerViewMesas);
        recyclerViewIMesas.clearOnScrollListeners();
        recyclerViewIMesas.clearOnChildAttachStateChangeListeners();
        recyclerViewIMesas.setAdapter(new AdaptadorIMesas(colorsIMesas, new C07087()));
        recyclerViewIMesas.setLayoutManager(new LinearLayoutManager(this));
    }

    public void f_Llenar_Recycler_Tikets(int enviado, int espera, int resolviendo, int resuelto) {
        colorsITikets.clear();
        String Enviado = String.valueOf(enviado);
        String Espera = String.valueOf(espera);
        String Resolviendo = String.valueOf(resolviendo);
        String Resuelto = String.valueOf(resuelto);
        colorsITikets.add(new ConstructorITikets("Enviado", Enviado, ""));
        colorsITikets.add(new ConstructorITikets("Espera", Espera, ""));
        colorsITikets.add(new ConstructorITikets("Resolviendo", Resolviendo, ""));
        colorsITikets.add(new ConstructorITikets("Resuelto", Resuelto, ""));
        recyclerViewITikets = (RecyclerView) findViewById(R.id.recyclerViewTikets);
        recyclerViewITikets.clearOnScrollListeners();
        recyclerViewITikets.clearOnChildAttachStateChangeListeners();
        recyclerViewITikets.setAdapter(new AdaptadorITikets(colorsITikets, new C07098()));
        recyclerViewITikets.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
            return true;
        }
        switch (item.getItemId()) {
            case 16908332:
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
