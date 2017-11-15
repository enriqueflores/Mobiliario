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
    ArrayList<String> LMesas = new ArrayList<String>();
    public static Typeface font;
    static RecyclerView recyclerView;
    public static FragmentManager fm;
    public static Frag tv;
    String b;
    private static List<ConstructorMesa> colors;
    String tipo_de_Usuario;
    public static String idMesaSel,idComanda,nomMesa;
    ArrayList<String> LidMesa = new ArrayList<String>();

    ArrayList<String> LidComanda = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuventas);
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        Login.progressDoalog.dismiss();

        colors = new ArrayList<ConstructorMesa>();

        f_Consulta_Keys();

        fm = getFragmentManager();
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void f_Consulta_Keys() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("mesas");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LMesas.clear();
                colors.clear();
                colors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    b = postSnapshot.getKey();
                    String estatus = (String) postSnapshot.child("estatus").getValue();
                    String nombre = (String) postSnapshot.child("nombre").getValue();
                    if (postSnapshot.child("nombre").exists()) {
                        LMesas.add(estatus + "°" + nombre + "°" + b);
                    }
                }
                f_Llenar_Recycler(LMesas);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }


    public void f_Llenar_Recycler(ArrayList<String> msj) {

        for (int i = 0; i <= msj.size() - 1; i++)
        {
            String aux[] = msj.get(i).split("°");
            String estatus = aux[0];
            String nmbre = aux[1];
            String id = aux[2];

            colors.add(new ConstructorMesa(nmbre, estatus, id));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();

        recyclerView.setAdapter(new AdaptadorMesa(colors, new ClickeadorMesa() {
            @Override
            public void onClick(View v, int position) {
                idComanda = Login.f_Sacar_Fecha_Completa_id();
                idMesaSel = String.valueOf(colors.get(position).getIcono());
                nomMesa = String.valueOf(colors.get(position).getName());
                for (int i=0;i<LidMesa.size();i++)
                {
                    if (LidMesa.get(i).equals(idMesaSel))
                    {
                        idComanda=LidComanda.get(i);
                    }
                }


                Intent intent = new Intent(getApplicationContext(), Carta.class);
                //intent.putExtra("parametro", tipoUsuario);
                startActivity(intent);
                f_estus_mesa("Ocupada");



            }
        }));

        recyclerView.setLayoutManager(new LinearLayoutManager(Mesa.this));
    }

    public void f_idComanda_activa()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(Login.restaurante).child("listaCompras");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LidMesa.clear();
                LidComanda.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idCom = postSnapshot.getKey();
                    String idmesa = (String) postSnapshot.child("mesa").getValue();
                    String existeLista = String.valueOf(postSnapshot.child("Lista").exists());


                        LidMesa.add(idmesa);
                        LidComanda.add(idCom);

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public static void f_estus_mesa( String estatus)
    {
        FirebaseDatabase databaseOcuparMesa = FirebaseDatabase.getInstance();
        DatabaseReference myRefOcuparMesa = databaseOcuparMesa.getReference().child(Login.restaurante)
                .child("mesas").child(idMesaSel).child("estatus");

        myRefOcuparMesa.setValue(estatus);
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
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        f_idComanda_activa();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}


