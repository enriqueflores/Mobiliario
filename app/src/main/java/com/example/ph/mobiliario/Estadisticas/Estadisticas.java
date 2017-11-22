package com.example.ph.mobiliario.Estadisticas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ph.mobiliario.R;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Estadisticas.Comandas.AdaptadorEComandas;
import com.example.ph.mobiliario.Estadisticas.Comandas.ClickeadorEComandas;
import com.example.ph.mobiliario.Estadisticas.Comandas.ConstructorEComandas;
import com.example.ph.mobiliario.Estadisticas.Mesas.AdaptadorEMesas;
import com.example.ph.mobiliario.Estadisticas.Mesas.ClickeadorEMesas;
import com.example.ph.mobiliario.Estadisticas.Mesas.ConstructorEMesas;
import com.example.ph.mobiliario.Estadisticas.tikets.AdaptadorETikets;
import com.example.ph.mobiliario.Estadisticas.tikets.ClickeadorETikets;
import com.example.ph.mobiliario.Estadisticas.tikets.ConstructorETikets;
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

public class Estadisticas extends AppCompatActivity {
    private static List<ConstructorEComandas> colorsEComandas;
    private static List<ConstructorEMesas> colorsEMesas;
    private static List<ConstructorETikets> colorsETikets;
    private static OnDateSetListener oyenteSelectorFechaA;
    private static OnDateSetListener oyenteSelectorFechaDe;
    static RecyclerView recyclerViewEComandas;
    static RecyclerView recyclerViewEMesas;
    static RecyclerView recyclerViewETikets;
    EditText f25A;
    EditText De;
    int f26aoA;
    int f27aoDe;
    int bloqueadas;
    int cocinando;
    int diaA;
    int diaDe;
    int entregadas;
    int enviado;
    int espera;
    int libres;
    Long longA;
    Long longDe;
    int mesA;
    int mesDe;
    int ocupadas;
    int pagando;
    int registradas;
    int resolviendo;
    int resuelto;

    class C02721 implements OnDateSetListener {
        C02721() {
        }

        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Estadisticas.this.f27aoDe = year;
            Estadisticas.this.mesDe = month + 1;
            Estadisticas.this.diaDe = day;
            Estadisticas.this.De.setText(Estadisticas.this.diaDe + "/" + Estadisticas.this.mesDe + "/" + Estadisticas.this.f27aoDe);
            String fecha = Estadisticas.this.De.getText().toString();
            Estadisticas.this.longDe = Long.valueOf(Estadisticas.this.F_fecha_TimeStap("00:00:33", fecha));
            String v = Estadisticas.this.F_TimeStamp_Fecha(String.valueOf(Estadisticas.this.longDe));
            Estadisticas.this.F_Consutar_idMaste_Comandas();
        }
    }

    class C02732 implements OnDateSetListener {
        C02732() {
        }

        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Estadisticas.this.f26aoA = year;
            Estadisticas.this.mesA = month + 1;
            Estadisticas.this.diaA = day;
            Estadisticas.this.f25A.setText(Estadisticas.this.diaA + "/" + Estadisticas.this.mesA + "/" + Estadisticas.this.f26aoA);
            String fecha = Estadisticas.this.f25A.getText().toString();
            Estadisticas.this.longA = Long.valueOf(Estadisticas.this.F_fecha_TimeStap("23:59:33", fecha));
            Estadisticas.this.F_Consutar_idMaste_Comandas();
        }
    }

    class C06953 implements ValueEventListener {
        C06953() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Estadisticas.this.entregadas = 0;
            Estadisticas.this.cocinando = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String d=postSnapshot.getKey();
                String strin="";
                for (int g = 2;g<d.length();g++) {
                    char letra = d.charAt(g);
                    strin=strin+letra;
                }
                Long id = Long.valueOf(strin);
                if (id.longValue() >= Estadisticas.this.longDe.longValue() && id.longValue() <= Estadisticas.this.longA.longValue()) {
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    Estadisticas estadisticas;
                    estadisticas = Estadisticas.this;
                    estadisticas.cocinando++;
                  /*  if (estatus.equals("cocinando")) {
                        estadisticas = Estadisticas.this;
                        estadisticas.cocinando++;
                    } else if (estatus.equals("entregado")) {
                        estadisticas = Estadisticas.this;
                        estadisticas.entregadas++;
                    }*/
                }
            }
            Estadisticas.this.F_Consutar_porpagar();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C06964 implements ValueEventListener {
        C06964() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Estadisticas.this.pagando = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String d=postSnapshot.getKey();
                String strin="";
                for (int g = 2;g<d.length();g++) {
                    char letra = d.charAt(g);
                    strin=strin+letra;
                }
                Long id = Long.valueOf(strin);
                if (id.longValue() >= Estadisticas.this.longDe.longValue() && id.longValue() <= Estadisticas.this.longA.longValue()) {
                    Estadisticas estadisticas = Estadisticas.this;
                    estadisticas.pagando++;
                }
            }
            Estadisticas.this.f_Llenar_Recycler_Comandas();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C06975 implements ValueEventListener {
        C06975() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Estadisticas.this.registradas = 0;
            Estadisticas.this.libres = 0;
            Estadisticas.this.ocupadas = 0;
            Estadisticas.this.bloqueadas = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String idtiket = postSnapshot.getKey();
                if (!(idtiket.equals("limiteReservacion") || idtiket.equals("periodoReservacion") || idtiket.equals("tiempoEspera"))) {
                    Estadisticas estadisticas = Estadisticas.this;
                    estadisticas.registradas++;
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    if (estatus.equals("Libre")) {
                        estadisticas = Estadisticas.this;
                        estadisticas.libres++;
                    } else if (estatus.equals("Ocupada")) {
                        estadisticas = Estadisticas.this;
                        estadisticas.ocupadas++;
                    } else if (estatus.equals("Bloqueada")) {
                        estadisticas = Estadisticas.this;
                        estadisticas.bloqueadas++;
                    }
                }
            }
            Estadisticas.this.f_Llenar_Recycler_Mesas(Estadisticas.this.libres, Estadisticas.this.ocupadas, Estadisticas.this.bloqueadas);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C06986 implements ValueEventListener {
        C06986() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Estadisticas.this.enviado = 0;
            Estadisticas.this.espera = 0;
            Estadisticas.this.resolviendo = 0;
            Estadisticas.this.resuelto = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Estadisticas.this.F_Consutar_idtiket(postSnapshot.getKey());
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C06997 implements ValueEventListener {
        C06997() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String idtiket = postSnapshot.getKey();
                String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                Estadisticas estadisticas;
                if (estatus.equals("Enviado")) {
                    estadisticas = Estadisticas.this;
                    estadisticas.enviado++;
                } else if (estatus.equals("Espera")) {
                    estadisticas = Estadisticas.this;
                    estadisticas.espera++;
                } else if (estatus.equals("Resolviendo")) {
                    estadisticas = Estadisticas.this;
                    estadisticas.resolviendo++;
                } else if (estatus.equals("Resuelto")) {
                    estadisticas = Estadisticas.this;
                    estadisticas.resuelto++;
                }
            }
            Estadisticas.this.f_Llenar_Recycler_Tikets(Estadisticas.this.enviado, Estadisticas.this.espera, Estadisticas.this.resolviendo, Estadisticas.this.resuelto);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07008 implements ClickeadorEComandas {
        C07008() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorEComandas) Estadisticas.colorsEComandas.get(position)).getCantidad());
        }
    }

    class C07019 implements ClickeadorEMesas {
        C07019() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorEMesas) Estadisticas.colorsEMesas.get(position)).getCantidad());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_estadisticas);
        getSupportActionBar().setTitle((CharSequence) "Estadísticas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.De = (EditText) findViewById(R.id.De);
        this.f25A = (EditText) findViewById(R.id.A);
        colorsETikets = new ArrayList();
        colorsEMesas = new ArrayList();
        colorsEComandas = new ArrayList();
        this.longDe = Long.valueOf(F_fecha_TimeStap("00:00:33", f_Sacar_Fecha_Actual()));
        this.longA = Long.valueOf(F_fecha_TimeStap("23:59:33", f_Sacar_Fecha_Actual()));
        F_Consutar_idMaste_Tikets();
        F_Consutar_idMaste_Mesas();
        F_Consutar_idMaste_Comandas();
        oyenteSelectorFechaDe = new C02721();
        oyenteSelectorFechaA = new C02732();
    }

    public long F_fecha_TimeStap(String hora, String fecha) {
        Date dateAux = null;
        try {
            dateAux = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fecha + " " + hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAux.getTime();
    }

    public String F_TimeStamp_Fecha(String id) {
        Date date = new Date(Long.parseLong(id));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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

    public void mostrarCalendarioDe(View view) {
        showDialog(0);
    }

    public void mostrarCalendarioA(View view) {
        showDialog(1);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                String[] fechaDe = f_Sacar_Fecha_Actual().split("/");
                this.diaDe = Integer.parseInt(fechaDe[0]);
                this.mesDe = Integer.parseInt(fechaDe[1]) - 1;
                this.f27aoDe = Integer.parseInt(fechaDe[2]);
                return new DatePickerDialog(this, oyenteSelectorFechaDe, this.f27aoDe, this.mesDe, this.diaDe);
            case 1:
                String[] fechaA = f_Sacar_Fecha_Actual().split("/");
                this.diaA = Integer.parseInt(fechaA[0]);
                this.mesA = Integer.parseInt(fechaA[1]) - 1;
                this.f26aoA = Integer.parseInt(fechaA[2]);
                return new DatePickerDialog(this, oyenteSelectorFechaA, this.f26aoA, this.mesA, this.diaA);
            default:
                return null;
        }
    }

    private void F_Consutar_idMaste_Comandas() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").addValueEventListener(new C06953());
    }

    private void F_Consutar_porpagar() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("porpagar").addValueEventListener(new C06964());
    }

    private void F_Consutar_idMaste_Mesas() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("mesas").addValueEventListener(new C06975());
    }

    private void F_Consutar_idMaste_Tikets() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").addValueEventListener(new C06986());
    }

    private void F_Consutar_idtiket(String idMaster) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").child(idMaster).addListenerForSingleValueEvent(new C06997());
    }

    public void f_Llenar_Recycler_Comandas() {
        colorsEComandas.clear();
        String Entregadas = String.valueOf(this.entregadas);
        String Cocinando = String.valueOf(this.cocinando);
        String Pagando = String.valueOf(this.pagando);
       // colorsEComandas.add(new ConstructorEComandas("Entregadas", Entregadas, ""));
        colorsEComandas.add(new ConstructorEComandas("Cocinando", Cocinando, ""));
        colorsEComandas.add(new ConstructorEComandas("Pagando", Pagando, ""));
        recyclerViewEComandas = (RecyclerView) findViewById(R.id.recyclerViewComandas);
        recyclerViewEComandas.clearOnScrollListeners();
        recyclerViewEComandas.clearOnChildAttachStateChangeListeners();
        recyclerViewEComandas.setAdapter(new AdaptadorEComandas(colorsEComandas, new C07008()));
        recyclerViewEComandas.setLayoutManager(new LinearLayoutManager(this));
    }

    public void f_Llenar_Recycler_Mesas(int libres, int ocupadas, int bloqueadas) {
        colorsEMesas.clear();
        String Libre = String.valueOf(libres);
        String Ocupadas = String.valueOf(ocupadas);
        String Bloqueadas = String.valueOf(bloqueadas);
        String Registradas = String.valueOf(this.registradas);
        colorsEMesas.add(new ConstructorEMesas("Libres", Libre, ""));
        colorsEMesas.add(new ConstructorEMesas("Ocupadas", Ocupadas, ""));
        colorsEMesas.add(new ConstructorEMesas("Bloqueadas", Bloqueadas, ""));
        colorsEMesas.add(new ConstructorEMesas("Registradas", Registradas, ""));
        recyclerViewEMesas = (RecyclerView) findViewById(R.id.recyclerViewMesas);
        recyclerViewEMesas.clearOnScrollListeners();
        recyclerViewEMesas.clearOnChildAttachStateChangeListeners();
        recyclerViewEMesas.setAdapter(new AdaptadorEMesas(colorsEMesas, new C07019()));
        recyclerViewEMesas.setLayoutManager(new LinearLayoutManager(this));
    }

    public void f_Llenar_Recycler_Tikets(int enviado, int espera, int resolviendo, int resuelto) {
        colorsETikets.clear();
        String Enviado = String.valueOf(enviado);
        String Espera = String.valueOf(espera);
        String Resolviendo = String.valueOf(resolviendo);
        String Resuelto = String.valueOf(resuelto);
        colorsETikets.add(new ConstructorETikets("Enviado", Enviado, ""));
        colorsETikets.add(new ConstructorETikets("Espera", Espera, ""));
        colorsETikets.add(new ConstructorETikets("Resolviendo", Resolviendo, ""));
        colorsETikets.add(new ConstructorETikets("Resuelto", Resuelto, ""));
        recyclerViewETikets = (RecyclerView) findViewById(R.id.recyclerViewTikets);
        recyclerViewETikets.clearOnScrollListeners();
        recyclerViewETikets.clearOnChildAttachStateChangeListeners();
        recyclerViewETikets.setAdapter(new AdaptadorETikets(colorsETikets, new ClickeadorETikets() {
            public void onClick(View v, int position) {
                String aux = String.valueOf(((ConstructorETikets) Estadisticas.colorsETikets.get(position)).getCantidad());
            }
        }));
        recyclerViewETikets.setLayoutManager(new LinearLayoutManager(this));
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
