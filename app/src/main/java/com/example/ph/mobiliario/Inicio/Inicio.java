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

public class Inicio extends AppCompatActivity {
    private static List<ConstructorITikets> colorsITikets;
    static RecyclerView recyclerViewITikets;
    int enviado,espera,resolviendo,resuelto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        colorsITikets = new ArrayList<ConstructorITikets>();
        F_Consutar_idMaste();
    }
    private void F_Consutar_idMaste() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets");

        myRefSoporte.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enviado=0;espera=0;resolviendo=0;resuelto=0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idmaster = postSnapshot.getKey();
                    F_Consutar_idtiket(idmaster);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void F_Consutar_idtiket(String idMaster) {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").child(idMaster);
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idtiket = postSnapshot.getKey();
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    if (estatus.equals("Enviado"))                    {
                        enviado++;
                    }
                    else if (estatus.equals("Espera"))                    {
                        espera++;
                    }
                    else if (estatus.equals("Resolviendo"))                    {
                        resolviendo++;
                    }
                    else if (estatus.equals("Resuelto"))                    {
                        resuelto++;
                    }
                }
                f_Llenar_Recycler(enviado,espera,resolviendo,resuelto);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void f_Llenar_Recycler(int enviado, int espera, int resolviendo, int resuelto) {
        colorsITikets.clear();
        //colors = new ArrayList<Constructor>();
        String Enviado= String.valueOf(enviado);
        String Espera= String.valueOf(espera);
        String Resolviendo= String.valueOf(resolviendo);
        String Resuelto= String.valueOf(resuelto);

        colorsITikets.add(new ConstructorITikets("Enviado", Enviado, "\uf29c"));
        colorsITikets.add(new ConstructorITikets("Espera", Espera, "\uf29c"));
        colorsITikets.add(new ConstructorITikets("Resolviendo", Resolviendo, "\uf29c"));
        colorsITikets.add(new ConstructorITikets("Resuelto", Resuelto, "\uf29c"));

        recyclerViewITikets = (RecyclerView) findViewById(R.id.recyclerViewTikets);
        recyclerViewITikets.clearOnScrollListeners();
        recyclerViewITikets.clearOnChildAttachStateChangeListeners();

        recyclerViewITikets.setAdapter(new AdaptadorITikets(colorsITikets, new ClickeadorITikets() {
            @Override
            public void onClick(View v, int position) {

                String aux = String.valueOf(colorsITikets.get(position).getCantidad());
                // Toast.makeText(MenuDinamico.this, aux, Toast.LENGTH_SHORT).show();

            }
        }));
        //VERTICAL
        recyclerViewITikets.setLayoutManager(new LinearLayoutManager(Inicio.this));

    }
}
