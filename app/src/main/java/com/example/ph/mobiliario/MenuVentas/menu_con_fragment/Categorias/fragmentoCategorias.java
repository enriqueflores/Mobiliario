package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.fragmentoPlatillos;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class fragmentoCategorias extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static List<ConstructorCategorias> colorsCategorias;
    static RecyclerView recyclerViewCategorias;
    public static String selCategoria = "nada";
    public static String selCategoriaNombre = "nada";
    ArrayList<String> LCategorias = new ArrayList();
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class C07302 implements ClickeadorCategorias {
        C07302() {
        }

        public void onClick(View v, int position) {
            fragmentoCategorias.selCategoria = String.valueOf(((ConstructorCategorias) fragmentoCategorias.colorsCategorias.get(position)).getTabla());
            fragmentoCategorias.selCategoriaNombre = String.valueOf(((ConstructorCategorias) fragmentoCategorias.colorsCategorias.get(position)).getName());
            fragmentoPlatillos fragmento2 = new fragmentoPlatillos();
            FragmentTransaction transicion2 = fragmentoCategorias.this.getActivity().getSupportFragmentManager().beginTransaction();
            transicion2.replace(R.id.contenedor, fragmento2);
            transicion2.commit();
            Carta.btnfr1.setBackgroundResource(R.drawable.fondo_frag_des_sel);
            Carta.btnfr2.setBackgroundResource(R.drawable.fondo_frag_select);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fr1, container, false);
        Carta.Frame = "fragmentoCategorias";
        f_Consulta_Categorias(v);
        return v;
    }

    private void f_Consulta_Categorias(final View v) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("menu").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                fragmentoCategorias.this.LCategorias.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.getKey();
                    String nombre = (String) postSnapshot.child("nombre").getValue();
                    if (!id.equals("null")) {
                        fragmentoCategorias.this.LCategorias.add(nombre + "°" + id);
                    }
                }
                fragmentoCategorias.this.f_Llenar_Recycler(fragmentoCategorias.this.LCategorias, v);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Llenar_Recycler(ArrayList<String> msj, View v) {
        colorsCategorias = new ArrayList();
        for (int i = 0; i <= msj.size() - 1; i++) {
            String[] aux = ((String) msj.get(i)).split("°");
            colorsCategorias.add(new ConstructorCategorias(aux[0], aux[1], ""));
        }
        recyclerViewCategorias = (RecyclerView) v.findViewById(R.id.recyclerViewCategorias);
        recyclerViewCategorias.clearOnScrollListeners();
        recyclerViewCategorias.clearOnChildAttachStateChangeListeners();
        recyclerViewCategorias.setAdapter(new AdaptadorCategorias(colorsCategorias, new C07302()));
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
