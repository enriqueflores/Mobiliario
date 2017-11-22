package com.example.ph.mobiliario.Actividad;

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
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Actividad extends AppCompatActivity {
    private static List<ConstructorActividad> colors;
    public static Typeface font;
    static RecyclerView recyclerView;
    ArrayList<String> Llogg = new ArrayList();
    String TipoConexion;
    String f21b;
    String star;

    class C06871 implements ValueEventListener {
        C06871() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Actividad.this.Llogg.clear();
            Actividad.colors.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Actividad.this.f21b = postSnapshot.getKey();
                String displayName = (String) postSnapshot.child("displayName").getValue();
                String fecha = (String) postSnapshot.child("fecha").getValue();
                String modulo = (String) postSnapshot.child("modulo").getValue();
                String tabla = (String) postSnapshot.child("tabla").getValue();
                String uid = (String) postSnapshot.child("uid").getValue();
                Actividad.this.Llogg.add(((String) postSnapshot.child("actividad").getValue()) + "°" + displayName + "°" + fecha + "°" + modulo + "°" + tabla + "°" + uid);
            }
            Actividad.this.f_Llenar_Recycler(Actividad.this.Llogg);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C06882 implements ClickeadorActividad {
        C06882() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorActividad) Actividad.colors.get(position)).getTabla());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_actividad);
        this.TipoConexion = "si";
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        colors = new ArrayList();
        this.star = "si";
        f_Consulta_Keys();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Actividad");
    }

    private void f_Consulta_Keys() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("logg").addValueEventListener(new C06871());
    }

    public void f_Llenar_Recycler(ArrayList<String> msj) {
        for (int i = msj.size() - 1; i >= 0; i--) {
            String[] aux = ((String) msj.get(i)).split("°");
            String actividad = aux[0];
            String displayName = aux[1];
            String fecha = aux[2];
            String modulo = aux[3];
            colors.add(new ConstructorActividad("Actividad:\t\t " + actividad + "\nFecha:\t\t " + fecha, "Modulo:\t\t " + modulo + "\nTabla:\t\t " + aux[4], "Nombre:\t\t " + displayName + "\nId:\t\t " + aux[5]));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.setAdapter(new AdaptadorActividad(colors, new C06882()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerActividad(this));
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.actividad, menu);
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

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        Login.f_line();
        this.TipoConexion = "si";
    }

    protected void onPause() {
        super.onPause();
        Login.f_offline();
        this.TipoConexion = "no";
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
