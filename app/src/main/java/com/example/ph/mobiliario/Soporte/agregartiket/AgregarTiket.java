package com.example.ph.mobiliario.Soporte.agregartiket;
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarTiket extends AppCompatActivity {
    EditText titulo, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tiket);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("xxx");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getActionBar().setDisplayHomeAsUpEnabled(true);


        titulo = (EditText) findViewById(R.id.txttitulo);
        descripcion = (EditText) findViewById(R.id.txdescripcion);
    }

    public void Abrir_Ticket(View v) {
        String Stitulo = titulo.getText().toString();
        String Sdescripcion = descripcion.getText().toString();
        if (!TextUtils.isEmpty(Sdescripcion) && !TextUtils.isEmpty(Stitulo)) {
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(Login.restaurante)
                    .child("tickets").child(Login.uidUsuario).child(f_Sacar_Fecha_Completa_id());

            myRef.child("descripcion").setValue(Sdescripcion);
            myRef.child("titulo").setValue(Stitulo);
            myRef.child("fecha").setValue(f_Sacar_Fecha_Completa_bonita());
            myRef.child("estatus").setValue("enviado");
            Login.f_logg("Levanto nuevo Ticket","/Soporte/","tickets");
            finish();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
