package com.example.ph.mobiliario.Soporte;
/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.SubMenuDinamico.Frag;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Soporte.agregartiket.AgregarTiket;
import com.example.ph.mobiliario.Soporte.chatTC.chatTC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tiket extends AppCompatActivity {
    String TipoConexion;
    EditText txtMensajeChat;
    private DatabaseReference mDatabase;
    public static String keySeleccionado;
    String[] arregloAdd, datosMenuHistorial;
    public static Typeface font;
    public String www;
    ArrayList<String> LMsj = new ArrayList<String>();
    ArrayList<String> LEmisor = new ArrayList<String>();
    ArrayList<String> LReceptor = new ArrayList<String>();
    ArrayList<String> LFecha = new ArrayList<String>();
    ArrayList<String> LEstatus = new ArrayList<String>();
    String Resultado, ResultadoArr;
    static RecyclerView recyclerView;
    public static FragmentManager fm;
    public static Frag tv;
    String b, v;
    int tamañoAd_permiso;
    public static String actAux, usuAux, tabAux, IcoAux;
    private static List<ConstructorTiket> colors;
    String ResultadoModulo, star;
    int i;
    ArrayList<String> pornstasTike = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket);
       // getSupportActionBar().setLogo(R.mipmap.ic_launcher);
       // getSupportActionBar().setDisplayUseLogoEnabled(true);
       // getSupportActionBar().setTitle("f_enviarPedido");
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        TipoConexion="si";
        colors = new ArrayList<ConstructorTiket>();
        star = "si";

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets");

        f_Consulta_Keys();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void f_Consulta_Keys() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").child(Login.uidUsuario);
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LMsj.clear();
                colors.clear();
                colors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String j = postSnapshot.getKey();
                    String titulo = (String) postSnapshot.child("titulo").getValue();
                    String fecha = (String) postSnapshot.child("fecha").getValue();
                    String descripcion = (String) postSnapshot.child("descripcion").getValue();
                    String estatus = (String) postSnapshot.child("estatus").getValue();
                    LMsj.add(titulo + "°" + estatus + "°" + fecha + "°" + descripcion+"°"+j);
                }
              //  f_Llenar_Recycler(LMsj);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void f_Llenar_Recycler(ArrayList<String> msj, ArrayList<String> pornstasTike) {
colors.clear();
                for (int i = msj.size() - 1; i >= 0; i--) {
                    String aux[] = msj.get(i).split("°");
                    String titulo = aux[0];
                    String estatus = aux[1];
                    String fecha = aux[2];
                    String descripcion = aux[3];
                    String jj = aux[4];
for (int y=0;y<pornstasTike.size();y++) {
    if (pornstasTike.get(y).equals(titulo))
    {
        estatus=estatus+" \nNUEVA NOTIFICACION";
    }
}
    colors.add(new ConstructorTiket(estatus, jj, titulo));


                }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();

        recyclerView.setAdapter(new AdaptadorTiket(colors, new ClickeadorTiket() {
            @Override
            public void onClick(View v, int position) {
                String aux = String.valueOf(colors.get(position).getTabla());
                f_Consulta_Keys_up(aux);
                Intent intent = new Intent(getApplicationContext(), chatTC.class);
                intent.putExtra("timestamp", aux);
                startActivity(intent);

            }
        }));
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(Tiket.this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(Historial.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerTiket(Tiket.this));

    }

    private void f_Consulta_Keys_up(final String aux) {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets")
                .child(Login.uidUsuario).child(aux).child("chat");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long qq;
                if (TipoConexion.equals("si")) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        qq = postSnapshot.getChildrenCount();
                        if (qq >= 5) {
                            String g = postSnapshot.getKey();
                            String msj = (String) postSnapshot.child("msj").getValue();
                            String fecha = (String) postSnapshot.child("fecha").getValue();
                            String emisor = (String) postSnapshot.child("emisor").getValue();
                            String receptor = (String) postSnapshot.child("receptor").getValue();
                            String estatus = (String) postSnapshot.child("estatus").getValue();

                            if (receptor.equals(Login.uidUsuario)) {
                                f_Cambiar_estatus(aux, g);
                            }
                        }
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    private void f_Cambiar_estatus(String aux, String g) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(Login.restaurante).child("tickets")
                .child(Login.uidUsuario).child(aux).child("chat").child(g);

        myRef.child("estatus").setValue("leido");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void f_Contador_chatsTike1()
    {

        FirebaseDatabase databaseContadorChats = FirebaseDatabase.getInstance();
        DatabaseReference myRefContadorChats = databaseContadorChats.getReference().child(Login.restaurante)
                .child("tickets").child(Login.uidUsuario);

        // Read from the database
        myRefContadorChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pornstasTike.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String z = postSnapshot.getKey();
                    String titulo = String.valueOf(postSnapshot.child("titulo").getValue());
                    f_Contador_chatsTike2(z,titulo);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    public void f_Contador_chatsTike2(final String zz, final String titulo)
    {
        FirebaseDatabase databaseContadorChats = FirebaseDatabase.getInstance();
        DatabaseReference myRefContadorChats = databaseContadorChats.getReference().child(Login.restaurante)
                .child("tickets").child(Login.uidUsuario).child(zz).child("chat");

        // Read from the database
        myRefContadorChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              //  pornstasTike.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String p = postSnapshot.getKey();

                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    String receptor = String.valueOf(postSnapshot.child("receptor").getValue());
                    String emisor = String.valueOf(postSnapshot.child("emisor").getValue());
                    if (receptor.equals(Login.uidUsuario)&&estatus.equals("no-leido"))
                    {

                        pornstasTike.add(titulo);
                    }
                }
                f_Llenar_Recycler(LMsj,pornstasTike);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    public String f_Sacar_Fecha_Completa_bonita() {
        Date d = new Date();
        String milisegundosparaKey = String.valueOf(d.getTime());
        SimpleDateFormat fecc = new SimpleDateFormat("d'-'MM'-'yyyy");
        String fechacComplString = fecc.format(d);
        Log.i("FECHA COMPLETA", fechacComplString);
        //SACAMOS LA HORA
        SimpleDateFormat ho = new SimpleDateFormat("h:mm:ss a");
        String horaString = ho.format(d);
        Log.i("HORA", horaString);
        String sFrecha = fechacComplString + "  " + horaString;
        //  fechaCompleta.setText(fecha);
        // SACAMOS EL DIA con letras
        SimpleDateFormat di = new SimpleDateFormat("EEEE");
        String currentDateTimeStrin = di.format(d);
        Log.i("dia con letras", currentDateTimeStrin);
        //SACAMOS EL MES con letras
        SimpleDateFormat me = new SimpleDateFormat("MMMM");
        String mesString = me.format(d);
        Log.i("mes con letras", mesString);
        //SACAMOS EL AÑO
        SimpleDateFormat an = new SimpleDateFormat("yyyy");
        String añoString = an.format(d);
        Log.i("Año", añoString);
        return sFrecha;
    }

    public String f_Sacar_Fecha_Completa_id() {
        Date d = new Date();
        String milisegundosparaKey = String.valueOf(d.getTime());
        SimpleDateFormat fecc = new SimpleDateFormat("d'-'MM'-'yyyy");
        String fechacComplString = fecc.format(d);
        Log.i("FECHA COMPLETA", fechacComplString);
        //SACAMOS LA HORA
        SimpleDateFormat ho = new SimpleDateFormat("h:mm:ss a");
        String horaString = ho.format(d);
        Log.i("HORA", horaString);
        String sFrecha = fechacComplString + "  " + horaString;
        //  fechaCompleta.setText(fecha);
        // SACAMOS EL DIA con letras
        SimpleDateFormat di = new SimpleDateFormat("EEEE");
        String currentDateTimeStrin = di.format(d);
        Log.i("dia con letras", currentDateTimeStrin);
        //SACAMOS EL MES con letras
        SimpleDateFormat me = new SimpleDateFormat("MMMM");
        String mesString = me.format(d);
        Log.i("mes con letras", mesString);
        //SACAMOS EL AÑO
        SimpleDateFormat an = new SimpleDateFormat("yyyy");
        String añoString = an.format(d);
        Log.i("Año", añoString);
        return milisegundosparaKey;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Login.f_line();
        f_Contador_chatsTike1();
        TipoConexion="si";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Login.f_offline();
        TipoConexion="no";
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tickets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.agragar) {

            Intent intent = new Intent(getApplicationContext(), AgregarTiket.class);
            //intent.putExtra("parametro", "");
            startActivity(intent);
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}


