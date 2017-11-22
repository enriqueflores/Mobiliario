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
//import com.example.ph.mobiliario.Estadisticas.Estadisticas;
import com.example.ph.mobiliario.Lista_Compras.listaCompras;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import com.example.ph.mobiliario.Estadisticas.Estadisticas;

public class Frag extends DialogFragment {
    private static List<ConstructorFrag> colorsFrag;
    static RecyclerView recyclerView;
    static View rootView;
    String Resultado;
    String ResultadoArr;
    String[] arrHeredado;
    String extras;
    String padre;
    int tam;

    class C07251 implements ValueEventListener {
        C07251() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String hijosdePadre = postSnapshot.getKey();
                Log.i("HIJOS wereber ", hijosdePadre);
                Frag.this.f_Consulta_Modulo(hijosdePadre);
            }
            Frag.this.tam = (int) dataSnapshot.getChildrenCount();
            Log.i("TAm", String.valueOf(Frag.this.tam));
        }

        public void onCancelled(DatabaseError databaseError) {
            Log.w("tag", "loadPost:onCancelled", databaseError.toException());
        }
    }

    class C07262 implements ValueEventListener {
        C07262() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
            }
            dataSnapshot.getValue();
            String mobil = String.valueOf(dataSnapshot.child("mobil").getValue());
            String key_Id=dataSnapshot.getKey();
            Log.i("key_id", dataSnapshot.getKey());
            String icono = String.valueOf(dataSnapshot.child("iconm").getValue());
            String estatus = String.valueOf(dataSnapshot.child("estatus").getValue());
            String descripcion = String.valueOf(dataSnapshot.child("description").getValue());
            String title = String.valueOf(dataSnapshot.child("title").getValue());
            String url = String.valueOf(dataSnapshot.child("url").getValue());
            String parent = String.valueOf(dataSnapshot.child("parent").getValue());
            if (mobil.equals("si")) {
                Log.i("mobil", mobil);
                Frag.this.f_Llenar_Recycler_Frag(new String[]{icono, estatus, descripcion, title, url, key_Id});
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Log.w("tag", "loadPost:onCancelled", databaseError.toException());
        }
    }

    class C07273 implements ClickeadorFrag {
        C07273() {
        }

        public void onClick(View v, int position) {
            String aux = String.valueOf(((ConstructorFrag) Frag.colorsFrag.get(position)).getName());
            if (aux.equals("Lista de compras")) {
                Frag.this.startActivity(new Intent(Frag.rootView.getContext(), listaCompras.class));
            }
            if (aux.equals("Comandas")) {
                Frag.this.startActivity(new Intent(Frag.rootView.getContext(), Mesa.class));
            }
            if (aux.equals("Preparación")) {
                Toast.makeText(Frag.rootView.getContext(), "Servicio disponible en pagina Web", 1).show();
            }
            if (aux.equals("Actividad")) {
                Frag.this.startActivity(new Intent(Frag.rootView.getContext(), Actividad.class));
            }
            if (aux.equals("Estadísticas")) {
                //Frag.this.startActivity(new Intent(Frag.rootView.getContext(), Estadisticas.class));
            }
            MenuDinamico.tv.dismiss();
        }
    }

    public Frag(String padre, String extras) {
        this.padre = padre;
        this.extras = extras;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_blank, container);
        colorsFrag = new ArrayList();
        f_Consulta_hijos(this.padre, this.extras);
        getDialog().setTitle(MenuDinamico.nombreGlobal);
        return rootView;
    }

    private void f_Consulta_hijos(String padre, String extras) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_permiso").child(extras).child(padre).addValueEventListener(new C07251());
    }

    private void f_Consulta_Modulo(String resultadoArr) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_modulo").child(resultadoArr).addValueEventListener(new C07262());
    }

    public void f_Llenar_Recycler_Frag(String[] datos) {
        String titulo = datos[3];
        String subtitulo = datos[2];
        String icono = datos[0];
        String url = datos[4];
        colorsFrag.add(new ConstructorFrag(titulo, datos[5], icono));
        if (colorsFrag.size() > this.tam) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            colorsFrag = new ArrayList();
            MenuDinamico.tv.dismiss();
            MenuDinamico.fm = getFragmentManager();
            MenuDinamico.tv = new Frag(this.padre, this.extras);
            MenuDinamico.tv.show(MenuDinamico.fm, "TV");
        }
        Log.i("tamaño", String.valueOf(colorsFrag.size()));
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewFrag);
        recyclerView.clearOnScrollListeners();
        recyclerView.setAdapter(new AdaptadorFrag(colorsFrag, new C07273()));
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.addItemDecoration(new DividerFrag(rootView.getContext()));
    }
}
