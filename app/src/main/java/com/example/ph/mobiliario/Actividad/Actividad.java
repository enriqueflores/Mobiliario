package com.example.ph.mobiliario.Actividad;

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
import android.widget.Toast;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;import com.google.firebase.database.ValueEventListener;import java.util.ArrayList;import java.util.List;

public class Actividad extends AppCompatActivity {
    public static Typeface font;
    ArrayList<String> Llogg = new ArrayList<String>();
    static RecyclerView recyclerView;
    String b;
    private static List<ConstructorActividad> colors;
    String  star;
    String TipoConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);
        TipoConexion="si";
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        colors = new ArrayList<ConstructorActividad>();
        star = "si";
        f_Consulta_Keys();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void f_Consulta_Keys() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("logg");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Llogg.clear();
                colors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    b = postSnapshot.getKey();
                    String actividad = (String) postSnapshot.child("actividad").getValue();
                    String displayName = (String) postSnapshot.child("displayName").getValue();
                    String fecha = (String) postSnapshot.child("fecha").getValue();
                    String modulo = (String) postSnapshot.child("modulo").getValue();
                    String tabla = (String) postSnapshot.child("tabla").getValue();
                    String uid = (String) postSnapshot.child("uid").getValue();
                    Llogg.add(actividad + "°" + displayName + "°" + fecha + "°" + modulo+ "°" + tabla+ "°" + uid);
                }
                f_Llenar_Recycler(Llogg);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void f_Llenar_Recycler(ArrayList<String> msj) {
        for (int i = msj.size() - 1; i >= 0; i--) {
            String aux[] = msj.get(i).split("°");
            String actividad = aux[0];
            String displayName = aux[1];
            String fecha = aux[2];
            String modulo = aux[3];
            String tabla = aux[4];
            String uid = aux[5];


                colors.add(new ConstructorActividad("Actividad:\t\t "+actividad+
                                                    "\nFecha:\t\t "+fecha, "Modulo:\t\t "+modulo+
                                                    "\nTabla:\t\t "+tabla, "Nombre:\t\t "+displayName+
                                                    "\nId:\t\t "+uid));


        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();

        recyclerView.setAdapter(new AdaptadorActividad(colors, new ClickeadorActividad() {
            @Override
            public void onClick(View v, int position) {

                String aux = String.valueOf(colors.get(position).getTabla());
                Toast.makeText(Actividad.this, aux, Toast.LENGTH_SHORT).show();

            }
        }));
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(Actividad.this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(Historial.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerActividad(Actividad.this));

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
