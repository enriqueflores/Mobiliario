package com.example.ph.mobiliario.MenuDinamico.SubMenuDinamico;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ph.mobiliario.Actividad.Actividad;
import com.example.ph.mobiliario.Lista_Compras.listaCompras;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ph.mobiliario.MenuDinamico.MenuDinamico.fm;
import static com.example.ph.mobiliario.MenuDinamico.MenuDinamico.tv;

/**
 * Created by ph on 21/07/2017.
 */

public class Frag extends DialogFragment {
    static RecyclerView recyclerView;
    String Resultado, ResultadoArr, padre, extras;
    static View rootView;
    private static List<ConstructorFrag> colorsFrag;
    String[] arrHeredado;
    int tam;


    public Frag(String padre, String extras) {
        this.padre = padre;
        this.extras = extras;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_blank, container);
        colorsFrag = new ArrayList<ConstructorFrag>();
        f_Consulta_hijos(padre, extras);
        //f_Llenar_Recycler_Frag(arrHeredado);

        this.getDialog().setTitle("Submenu");
        return rootView;
    }

    private void f_Consulta_hijos(String padre, String extras) {

        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante)
                .child("ad_permiso").child(extras).child(padre);
        //sirve para traer  nodo completo
        // My top posts by number of stars
        myRefSoporte.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    String hijosdePadre = postSnapshot.getKey();
                    Log.i("HIJOS wereber ", hijosdePadre);
                    f_Consulta_Modulo(hijosdePadre);
                }
                tam = (int) dataSnapshot.getChildrenCount();
                Log.i("TAm", String.valueOf(tam));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void f_Consulta_Modulo(String resultadoArr) {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_modulo");
        //sirve para traer  nodo completo
        // My top posts by number of stars
        myRefSoporte.child(resultadoArr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                }
                dataSnapshot.getValue();
                String mobil = String.valueOf(dataSnapshot.child("mobil").getValue());
                String key_Id = dataSnapshot.getKey();
                Log.i("key_id", key_Id);
                String icono = String.valueOf(dataSnapshot.child("iconm").getValue());
                String estatus = String.valueOf(dataSnapshot.child("estatus").getValue());
                String descripcion = String.valueOf(dataSnapshot.child("description").getValue());
                String title = String.valueOf(dataSnapshot.child("title").getValue());
                String url = String.valueOf(dataSnapshot.child("url").getValue());
                String parent = String.valueOf(dataSnapshot.child("parent").getValue());
                //ResultadoModulo = String.valueOf(dataSnapshot.getValue());
                if (mobil.equals("si"))
                {
                    Log.i("mobil",mobil);
                    String[] datosMenu = {icono, estatus, descripcion, title, url,key_Id};
                    f_Llenar_Recycler_Frag(datosMenu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void f_Llenar_Recycler_Frag(final String[] datos) {
        // colorsFrag = new ArrayList<ConstructorFrag>();
        final String titulo = datos[3],
                subtitulo = datos[2],
                icono = datos[0],
                url = datos[4];
        final String keyId = datos[5];

        colorsFrag.add(new ConstructorFrag(titulo, keyId, icono));
        if (colorsFrag.size() > tam) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            colorsFrag = new ArrayList<ConstructorFrag>();
            //colorsFrag.clear();
            tv.dismiss();
            //f_Consulta_hijos(padre);
            fm = getFragmentManager();
            tv = new Frag(padre, extras);
            tv.show(fm, "TV");

            // Toast.makeText(MenuDinamico.this, "colors se paso",Toast.LENGTH_SHORT).show();
        }
        Log.i("tamaño", String.valueOf(colorsFrag.size()));


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewFrag);
        recyclerView.clearOnScrollListeners();
        recyclerView.setAdapter(new AdaptadorFrag(colorsFrag, new ClickeadorFrag() {
            @Override
            public void onClick(View v, int position) {
                String aux = String.valueOf(colorsFrag.get(position).getName());
                Toast.makeText(rootView.getContext(), url + "  posicion: " + position, Toast.LENGTH_SHORT).show();
                if (url.equals("/listaCompras/")) {
                    Intent intent = new Intent(rootView.getContext(), listaCompras.class);//
                    startActivity(intent);
                }
                if (url.equals("/comandas/")) {
                    Intent intent = new Intent(rootView.getContext(), Mesa.class);//
                    startActivity(intent);
                }
                if (url.equals("/home/")) {
                  //  Intent intent = new Intent(rootView.getContext(), Home.class);//
                   // startActivity(intent);
                }
                if (aux.equals("Actividad")) {
                    Intent intent = new Intent(rootView.getContext(), Actividad.class);//
                    startActivity(intent);
                }
            }

        }));
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(Historial.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerFrag(rootView.getContext()));

    }



}
