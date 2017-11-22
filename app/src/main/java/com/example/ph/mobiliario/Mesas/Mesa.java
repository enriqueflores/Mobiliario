package com.example.ph.mobiliario.Mesas;

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
import android.widget.Toast;


import com.example.ph.mobiliario.Chat.DividerChat;
import com.example.ph.mobiliario.EditPerfi.CambiandoDatos;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.MenuDinamico.SubMenuDinamico.Frag;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mesa extends AppCompatActivity {
    private static List<ConstructorMesa> colors;
    public static FragmentManager fm;
    public static Typeface font;
    public static String idComanda;
    public static String idMesaSel;
    public static String nomMesa;
    static RecyclerView recyclerView;
    public static Frag tv;
    ArrayList<String> LMesas = new ArrayList();
    ArrayList<String> LidComanda = new ArrayList();
    ArrayList<String> LidMesa = new ArrayList();
    String f33b;
    String tipo_de_Usuario;

    class C07491 implements ValueEventListener {
        C07491() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Mesa.this.LMesas.clear();
            Mesa.colors.clear();
            Mesa.colors.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Mesa.this.f33b = postSnapshot.getKey();
                String estatus = (String) postSnapshot.child("estatus").getValue();
                String nombre = (String) postSnapshot.child("nombre").getValue();
                if (postSnapshot.child("nombre").exists()) {
                    Mesa.this.LMesas.add(estatus + "°" + nombre + "°" + Mesa.this.f33b);
                }
            }
            Mesa.this.f_Llenar_Recycler(Mesa.this.LMesas);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07502 implements ClickeadorMesa {
        C07502() {
        }

        public void onClick(View v, int position) {
            int numeroDB= Integer.parseInt(Login.restaurante);
            String numero;
            if (numeroDB<=9)
            {
                 numero="0"+numeroDB;
            }
            else{
                 numero=numeroDB+"";
            }
            Mesa.idComanda = numero+Login.f_Sacar_Fecha_Completa_id();
            Mesa.idMesaSel = String.valueOf(((ConstructorMesa) Mesa.colors.get(position)).getIcono());
            Mesa.nomMesa = String.valueOf(((ConstructorMesa) Mesa.colors.get(position)).getName());
            for (int i = 0; i < Mesa.this.LidMesa.size(); i++) {
                if (((String) Mesa.this.LidMesa.get(i)).equals(Mesa.idMesaSel)) {
                    Mesa.idComanda = (String) Mesa.this.LidComanda.get(i);
                }
            }
            Mesa.this.startActivity(new Intent(Mesa.this.getApplicationContext(), Carta.class));
            Mesa.f_estus_mesa("Ocupada");
        }
    }

    class C07513 implements ValueEventListener {
        C07513() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Mesa.this.LidMesa.clear();
            Mesa.this.LidComanda.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String idCom = postSnapshot.getKey();
                String idmesa = (String) postSnapshot.child("mesa").getValue();
                String existeLista = String.valueOf(postSnapshot.child("Lista").exists());
                Mesa.this.LidMesa.add(idmesa);
                Mesa.this.LidComanda.add(idCom);
            }
        }

        public void onCancelled(DatabaseError error) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_menuventas);
        getSupportActionBar().setTitle((CharSequence) "Mesas");
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        Login.progressDoalog.dismiss();
        colors = new ArrayList();
        f_Consulta_Keys();
        fm = getFragmentManager();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void f_Consulta_Keys() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("mesas").addValueEventListener(new C07491());
    }

    public void f_Llenar_Recycler(ArrayList<String> msj) {
        for (int i = 0; i <= msj.size() - 1; i++) {
            String[] aux = ((String) msj.get(i)).split("°");
            String estatus = aux[0];
            colors.add(new ConstructorMesa(aux[1], estatus, aux[2]));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.setAdapter(new AdaptadorMesa(colors, new C07502()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void f_idComanda_activa() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").addValueEventListener(new C07513());
    }

    public static void f_estus_mesa(String estatus) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("mesas").child(idMesaSel).child("estatus").setValue(estatus);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Login.f_Salir();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
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

    protected void onResume() {
        super.onResume();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onPostResume() {
        super.onPostResume();
    }

    protected void onStart() {
        super.onStart();
        f_idComanda_activa();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
