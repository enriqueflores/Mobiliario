package com.example.ph.mobiliario.Lista_Compras;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ph.mobiliario.Actividad.Actividad;
import com.example.ph.mobiliario.Actividad.AdaptadorActividad;
import com.example.ph.mobiliario.Actividad.ClickeadorActividad;
import com.example.ph.mobiliario.Actividad.ConstructorActividad;
import com.example.ph.mobiliario.Actividad.DividerActividad;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listaCompras extends AppCompatActivity {
    public static Typeface font;
    ArrayList<String> LlistaCom = new ArrayList<String>();
    static RecyclerView recyclerViewListaCompras;
    String b;
    private static List<ConstructorListaCompras> colors;
    String  star;
    String TipoConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);
        TipoConexion="si";
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        colors = new ArrayList<ConstructorListaCompras>();
        star = "si";
        f_Consulta_Keys();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void f_Consulta_Keys() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LlistaCom.clear();
                colors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idClass = postSnapshot.getKey();
                 f_Saber_mps(idClass);
                   // LlistaCom.add(actividad + "°" + displayName + "°" + fecha + "°" + modulo+ "°" + tabla+ "°" + uid);
                }
               // f_Llenar_Recycler(LlistaCom);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void f_Saber_mps(String idClass) {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(idClass).child("mp");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //LlistaCom.clear();
                //colors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idProducto = postSnapshot.getKey();
                    String cmax = String.valueOf(postSnapshot.child("cmax").getValue());
                    String cmin = String.valueOf(postSnapshot.child("cmin").getValue());
                    String descripcion = String.valueOf(postSnapshot.child("descripcion").getValue());
                    String existencia = String.valueOf(postSnapshot.child("existencia").getValue());
                    String nombre = String.valueOf(postSnapshot.child("nombre").getValue());
                    String tipo = String.valueOf(postSnapshot.child("tipo").getValue());
                    String umedida = String.valueOf(postSnapshot.child("umedida").getValue());

                    int max= Integer.parseInt(cmax);
                    int min= Integer.parseInt(cmin);
                    int exist= Integer.parseInt(existencia);
                    if (exist<=min) {
                        LlistaCom.add("Cantidad max: " + cmax + "°" +
                                "Cantidad min: " + cmin + "°" +
                                "Descripcion: " + descripcion + "°" +
                                "Existencia; " + existencia + "°" +
                                "Nombre: " + nombre + "°" +
                                "Tipo: " + tipo + "°" +
                                "Unid. de medida: " + umedida);
                    }
                }
                 f_Llenar_Recycler(LlistaCom);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void f_Llenar_Recycler(ArrayList<String> msj) {
        for (int i = msj.size() - 1; i >= 0; i--) {
            String aux[] = msj.get(i).split("°");
            String cmax = aux[0];
            String cmin = aux[1];
            String descripcion = aux[2];
            String existencia = aux[3];
            String nombre = aux[4];
            String tipo = aux[5];
            String umedida = aux[6];

            colors.add(new ConstructorListaCompras(cmax,cmin,descripcion,existencia,nombre,tipo,umedida));


        }

        recyclerViewListaCompras = (RecyclerView) findViewById(R.id.recyclerViewListaCompras);
        recyclerViewListaCompras.clearOnScrollListeners();
        recyclerViewListaCompras.clearOnChildAttachStateChangeListeners();

        recyclerViewListaCompras.setAdapter(new AdaptadorListaCompras(colors, new ClickeadorListaCompras() {
            @Override
            public void onClick(View v, int position) {

                String aux = String.valueOf(colors.get(position).getCmax());
                Toast.makeText(listaCompras.this, aux, Toast.LENGTH_SHORT).show();

            }
        }));
        recyclerViewListaCompras.setLayoutManager(new LinearLayoutManager(listaCompras.this));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Login.f_Salir();
            finish();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                //  Intent singleBlogIntent = new Intent(this, Usuarios.class);

                //startActivity(singleBlogIntent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Login.f_line();
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
}
