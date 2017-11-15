package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias.fragmentoCategorias;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Sub.Ingr;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class fragmentoPlatillos extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static List<ConstructorPlatillos> colorsPlatillos;
    public static String existenciaGlobal;
    static RecyclerView recyclerViewPlatillos;
    ArrayList<String> LPlatillos = new ArrayList();
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    class C03053 implements OnClickListener {
        C03053() {
        }

        public void onClick(DialogInterface dialog, int which) {
            fragmentoPlatillos.this.EliminarIngradientes();
        }
    }

    class C03064 implements OnClickListener {
        C03064() {
        }

        public void onClick(DialogInterface dialog, int which) {
            fragmentoPlatillos.this.AgragarIngradientes();
        }
    }

    class C03075 implements OnMultiChoiceClickListener {
        C03075() {
        }

        public void onClick(DialogInterface dialog, int pocicion, boolean isChecked) {
        }
    }

    class C03097 implements OnMultiChoiceClickListener {
        C03097() {
        }

        public void onClick(DialogInterface dialog, int pocicion, boolean isChecked) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class C07422 implements ClickeadorPlatillos {
        C07422() {
        }

        public void onClick(View v, int position) {
            Ingr.LPrecioModificable.clear();
            Ingr.LAuxNombre.clear();
            Ingr.LAuxCantidad.clear();
            Ingr.LAuxIdIngre.clear();
            String SelNombre = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getNombre());
            String SelDestino = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getDestino());
            String SelIdClasificacion = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getExixtencia());
            String SelTipo = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getTipo());
            String SelPrecio = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getPrecio());
            String SelIdPlatillo = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getIdPlatillo());
            fragmentoPlatillos.existenciaGlobal = String.valueOf(((ConstructorPlatillos) fragmentoPlatillos.colorsPlatillos.get(position)).getDescripcion());
            if (!SelTipo.equals("mp")) {
                Carta.f_Lanzar_Cambio_Ingredientes(SelIdClasificacion, SelTipo, SelNombre, SelPrecio, SelIdPlatillo, SelDestino, "", "", "", "");
            }
        }
    }

    public static fragmentoPlatillos newInstance(String param1, String param2) {
        fragmentoPlatillos fragment = new fragmentoPlatillos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fr2, container, false);
        Carta.Frame = "fragmentoPlatillos";
        Carta.btnfr2.setText(fragmentoCategorias.selCategoriaNombre);
        f_Consulta_Menu(fragmentoCategorias.selCategoria, v);
        return v;
    }

    private void f_Consulta_Menu(String item, final View v) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("menu").child(item).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                fragmentoPlatillos.this.LPlatillos.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatillo = postSnapshot.getKey();
                    if (!(idPlatillo.equals("descripcion") || idPlatillo.equals("nombre"))) {
                        String nombre = String.valueOf(postSnapshot.child("nombre").getValue());
                        String descripcion = String.valueOf(postSnapshot.child("descripcion").getValue());
                        String existencia = String.valueOf(postSnapshot.child("existencia").getValue());
                        String clasificacion1 = String.valueOf(postSnapshot.child("clasificacionRecursos").getValue());
                        String precio = String.valueOf(postSnapshot.child("precio").getValue());
                        String destino = String.valueOf(postSnapshot.child("destino").getValue());
                        String tipo = String.valueOf(postSnapshot.child("tipo").getValue());
                        if (!(TextUtils.isEmpty(nombre) || existencia.equals("0"))) {
                            fragmentoPlatillos.this.LPlatillos.add(nombre + "°" + descripcion + "°" + existencia + "°" + clasificacion1 + "°" + precio + "°" + tipo + "°" + idPlatillo + "°" + destino);
                        }
                        if (!TextUtils.isEmpty(nombre) && tipo.equals("pt") && existencia.equals("0")) {
                            fragmentoPlatillos.this.LPlatillos.add(nombre + "°" + descripcion + "°" + existencia + "°" + clasificacion1 + "°" + precio + "°" + tipo + "°" + idPlatillo + "°" + destino);
                        }
                        if (!TextUtils.isEmpty(nombre) && tipo.equals("pa") && existencia.equals("0")) {
                            fragmentoPlatillos.this.LPlatillos.add(nombre + "°" + descripcion + "°" + existencia + "°" + clasificacion1 + "°" + precio + "°" + tipo + "°" + idPlatillo + "°" + destino);
                        }
                    }
                }
                fragmentoPlatillos.this.f_Llenar_Recycler(fragmentoPlatillos.this.LPlatillos, v);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Llenar_Recycler(ArrayList<String> msj, View v) {
        colorsPlatillos = new ArrayList();
        for (int i = 0; i <= msj.size() - 1; i++) {
            String[] aux = ((String) msj.get(i)).split("°");
            String nombre = aux[0];
            String descripcion = aux[1];
            String existencia = aux[2];
            colorsPlatillos.add(new ConstructorPlatillos(nombre, aux[3], descripcion, existencia, aux[4], aux[5], aux[6], aux[7]));
        }
        recyclerViewPlatillos = (RecyclerView) v.findViewById(R.id.recyclerViewPlatillos);
        recyclerViewPlatillos.clearOnScrollListeners();
        recyclerViewPlatillos.clearOnChildAttachStateChangeListeners();
        recyclerViewPlatillos.setAdapter(new AdaptadorPlatillos(colorsPlatillos, new C07422()));
        recyclerViewPlatillos.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void AlerIngredientes() {
        Builder builder = new Builder(getContext());
        builder.setTitle((CharSequence) "Ingredientes").setMessage((CharSequence) "Elije la accion de los ingredientes:").setPositiveButton((CharSequence) "Agregar", new C03064()).setNegativeButton((CharSequence) "Eliminar", new C03053());
        builder.show();
    }

    public void EliminarIngradientes() {
        Builder builder = new Builder(getContext());
        builder.setTitle((CharSequence) "Agrega mas sabor...");
        final CharSequence[] animals = new String[]{"salsa", "queso", "crema", "silantro", "aguacate"};
        final boolean[] checkedItems = new boolean[]{true, true, true, true, true};
        builder.setMultiChoiceItems(animals, checkedItems, new C03075());
        builder.setPositiveButton((CharSequence) "Agragar(los)", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < animals.length; i++) {
                    if (checkedItems[i]) {
                        //Log.i("seleccion", animals[i]);
                    }
                }
            }
        });
        builder.setNegativeButton((CharSequence) "Cancelar", null);
        builder.create().show();
    }

    public void AgragarIngradientes() {
        Builder builder = new Builder(getContext());
        builder.setTitle((CharSequence) "Agrega mas sabor...");
        final CharSequence[] animals = new String[]{"salsa", "queso", "crema", "silantro", "aguacate"};
        final boolean[] checkedItems = new boolean[]{false, false, false, false, false};
        builder.setMultiChoiceItems(animals, checkedItems, new C03097());
        builder.setPositiveButton((CharSequence) "Agragar(los)", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < animals.length; i++) {
                    if (checkedItems[i]) {
                        //Log.i("seleccion", animals[i]);
                    }
                }
            }
        });
        builder.setNegativeButton((CharSequence) "Cancelar", null);
        builder.create().show();
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

