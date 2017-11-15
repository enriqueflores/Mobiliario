package com.example.ph.mobiliario.Soporte.chatTC;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.SubMenuDinamico.Frag;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Soporte.agregartiket.AgregarTiket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chatTC extends AppCompatActivity {

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
    private static List<ConstructorTC> colors;
    String ResultadoModulo, star,timestamp;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_tc);

        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        txtMensajeChat = (EditText) findViewById(R.id.txtMensajeChat);
        colors = new ArrayList<ConstructorTC>();
        star = "si";

        timestamp = getIntent().getExtras().getString("timestamp");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets")
                .child(Login.uidUsuario).child(timestamp).child("chat");;

        f_Consulta_Keys();

        Toast.makeText(chatTC.this, timestamp, Toast.LENGTH_SHORT).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabChattc);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msj = txtMensajeChat.getText().toString();
                if (!TextUtils.isEmpty(msj)) {

                    String id = f_Sacar_Fecha_Completa_id();
                    DatabaseReference newPOst = mDatabase.push();
                    // newPOst.child("title").setValue("title_val");

                    mDatabase.child(id).child("msj").setValue(msj);
                    //mDatabase.child(id).child("receptor").setValue(keySeleccionado);
                    mDatabase.child(id).child("receptor").setValue("3w4Cvs6cIhUlMuNZWQQO1EnqXbA3");
                    mDatabase.child(id).child("fecha").setValue(f_Sacar_Fecha_Completa_bonita());
                    mDatabase.child(id).child("estatus").setValue("no-leido");
                    mDatabase.child(id).child("emisor").setValue(Login.uidUsuario);

                    txtMensajeChat.setText(null);
                    Login.f_logg("Envio mensaje desde el Ticket: "+timestamp,"/Soporte/","tickets");
                }
            }
        });

        txtMensajeChat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                boolean handled = false;
                if (arg1 == 6) {
                    String msj = txtMensajeChat.getText().toString();
                    if (!TextUtils.isEmpty(msj)) {

                        String id = f_Sacar_Fecha_Completa_id();
                        DatabaseReference newPOst = mDatabase.push();
                        // newPOst.child("title").setValue("title_val");

                        mDatabase.child(id).child("msj").setValue(msj);
                        //mDatabase.child(id).child("receptor").setValue(keySeleccionado);
                        mDatabase.child(id).child("receptor").setValue("3w4Cvs6cIhUlMuNZWQQO1EnqXbA3");
                        mDatabase.child(id).child("fecha").setValue(f_Sacar_Fecha_Completa_bonita());
                        mDatabase.child(id).child("estatus").setValue("no-leido");
                        mDatabase.child(id).child("emisor").setValue(Login.uidUsuario);

                        txtMensajeChat.setText(null);
                        Login.f_logg("Envio mensaje desde el Ticket: "+timestamp,"/Soporte/","tickets");
                    }


                    handled = true;
                }
                return handled;
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void f_Consulta_Keys() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets")
        .child(Login.uidUsuario).child(timestamp).child("chat");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LMsj.clear();
                colors.clear();
                colors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    b = postSnapshot.getKey();
                    String msj = (String) postSnapshot.child("msj").getValue();
                    String fecha = (String) postSnapshot.child("fecha").getValue();
                    String emisor = (String) postSnapshot.child("emisor").getValue();
                    String receptor = (String) postSnapshot.child("receptor").getValue();
                    String estatus = (String) postSnapshot.child("estatus").getValue();
                    LMsj.add(msj + "°" + estatus + "°" + emisor + "°" + receptor);

                }
                f_Llenar_Recycler(LMsj);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }




    public void f_Llenar_Recycler(ArrayList<String> msj) {
        for (int i = 0; i <= msj.size() - 1; i++) {
            String aux[] = msj.get(i).split("°");
            String mensaje = aux[0];
            String estatus = aux[1];
            String emisor = aux[2];
            String receptor = aux[3];

         //   if (emisor.equals(Login.uidUsuario) && receptor.equals(keySeleccionado)) {
                colors.add(new ConstructorTC(mensaje, estatus, emisor));
          /*  } else if (emisor.equals(keySeleccionado) && receptor.equals(Login.uidUsuario)) {
                colors.add(new ConstructorTC(mensaje, estatus, emisor));
            }*/

        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();

        recyclerView.setAdapter(new AdaptadorTC(colors, new ClickeadorTC() {
            @Override
            public void onClick(View v, int position) {

                String aux = String.valueOf(colors.get(position).getTabla());
                Toast.makeText(chatTC.this, aux, Toast.LENGTH_SHORT).show();

            }
        }));
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(chatTC.this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(Historial.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerTC(chatTC.this));
        recyclerView.scrollToPosition(colors.size()-1);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        Login.f_offline();
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
        //getMenuInflater().inflate(R.menu.menu_tickets, menu);
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


