package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias.fragmentoCategorias;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.AdaptadorPlatillos;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes.modif.modifi;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras.fragmentoCompras;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class paquetes extends DialogFragment {
    public static ArrayList<String> LAuxCan = new ArrayList();
    public static ArrayList<String> LAuxCantidad = new ArrayList();
    public static ArrayList<String> LAuxClasificacionIngredientes = new ArrayList();
    public static ArrayList<String> LAuxIdIngre = new ArrayList();
    public static ArrayList<ArrayList<String>> LAuxLista = new ArrayList();
    public static ArrayList<String> LAuxNombre = new ArrayList();
    public static ArrayList<String> LAuxTipo = new ArrayList();
    public static ArrayList<String> LAuxUmedida = new ArrayList();
    public static ArrayList<String> LPrecioModificable = new ArrayList();
    static String SelDestino;
    static String SelIdPlatillo;
    static String SelNombre;
    public static String SelPrecio;
    private static List<ConstructorPaquetes> colorsSub;
    public static FragmentManager fmModifi;
    static int porno = 0;
    static RecyclerView recyclerViewSub;
    static View rootView;
    static String selIdClasificacion;
    static String tipo;
    public static modifi tvModifi;
    static ArrayList<String> unoCan = new ArrayList();
    static ArrayList<String> unoCantidad = new ArrayList();
    static ArrayList<String> unoClasificacionIngredientes = new ArrayList();
    static ArrayList<String> unoIdIngre = new ArrayList();
    static ArrayList<String> unoNombre = new ArrayList();
    static ArrayList<String> unoPrecioModificable = new ArrayList();
    static ArrayList<String> unoTipo = new ArrayList();
    static ArrayList<String> unoUmedida = new ArrayList();
    ArrayList<String> LIngredientes = new ArrayList();
    Button btnEditarIngredientes;
    Button btnEditarIngredientes2;
    int f12c;
    SharedPreferences pref;
    int variable;

    class C02951 implements OnClickListener {
        C02951() {
        }

        public void onClick(View view) {
            paquetes.this.f_agre_Dism_ingre();
        }
    }

    class C02962 implements OnClickListener {
        C02962() {
        }

        public void onClick(View view) {
            paquetes.this.f_agre_Dism_ingre();
        }
    }

    class C02986 implements DialogInterface.OnClickListener {
        C02986() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C07364 implements ValueEventListener {
        C07364() {
        }

        @RequiresApi(api = 23)
        public void onDataChange(DataSnapshot dataSnapshot) {
            int su;
            paquetes.colorsSub.clear();
            paquetes.LAuxNombre.clear();
            paquetes.LAuxCantidad.clear();
            paquetes.LAuxIdIngre.clear();
            paquetes.this.LIngredientes.clear();
            ArrayList<Integer> nivelesAUX = new ArrayList();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                nivelesAUX.add(Integer.valueOf(Integer.parseInt(String.valueOf(postSnapshot.child("nivel").getValue()))));
            }
            for (int q = 0; q < nivelesAUX.size(); q++) {
                int aux = ((Integer) nivelesAUX.get(q)).intValue();
                int j = 0;
                for (int b = 0; b < nivelesAUX.size(); b++) {
                    if (((Integer) nivelesAUX.get(b)).intValue() == aux) {
                        if (j == 1) {
                            nivelesAUX.remove(q);
                        }
                        j = 1;
                    }
                }
            }
            Editor editor = paquetes.this.pref.edit();
            for (su = 0; su <= nivelesAUX.size(); su++) {
                editor.putInt((su + 1) + "", -1);
                editor.commit();
            }
            for (su = 0; su <= nivelesAUX.size(); su++) {
                for (DataSnapshot postSnapshot2 : dataSnapshot.getChildren()) {
                    String id = postSnapshot2.getKey();
                    int count = (int) dataSnapshot.getChildrenCount();
                    String nombre = String.valueOf(postSnapshot2.child("nombre").getValue());
                    String cantidad = String.valueOf(postSnapshot2.child("cantidad").getValue());
                    String clasificacionIngrediente = String.valueOf(postSnapshot2.child("clasificacionIngrediente").getValue());
                    String tipo = String.valueOf(postSnapshot2.child("tipo").getValue());
                    String umedida = String.valueOf(postSnapshot2.child("umedida").getValue());
                    String nivel = String.valueOf(postSnapshot2.child("nivel").getValue());
                    if (Integer.parseInt(nivel) == su) {
                        paquetes.this.LIngredientes.add(nombre + "°" + cantidad + "°" + clasificacionIngrediente + "°" + tipo + "°" + umedida + "°" + id + "°" + nivel);
                    }
                }
                paquetes.this.f_Llenar_Recycler_Frag(paquetes.this.LIngredientes);
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Log.w("tag", "loadPost:onCancelled", databaseError.toException());
        }
    }

    class C07377 implements ClickeadorPaquetes {
        C07377() {
        }

        public void onClick(View v, int position) {
        }
    }

    public paquetes(String selIdClasificacion, String tipo, String Selnombre, String SelPrecio, String SelIdPlatillo, String SelDestino) {
        selIdClasificacion = selIdClasificacion;
        tipo = tipo;
        SelNombre = Selnombre;
        SelPrecio = SelPrecio;
        SelIdPlatillo = SelIdPlatillo;
        SelDestino = SelDestino;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_paquetes, container);
        this.btnEditarIngredientes2 = (Button) rootView.findViewById(R.id.btnEditarIngredientes2);
        this.btnEditarIngredientes = (Button) rootView.findViewById(R.id.btnEditarIngredientes);
        colorsSub = new ArrayList();
        f_Consulta_hijos_Radio();
        this.btnEditarIngredientes.setOnClickListener(new C02951());
        this.btnEditarIngredientes2.setOnClickListener(new C02962());
        this.pref = rootView.getContext().getSharedPreferences("data", 0);
        getDialog().setTitle("Productos");
        return rootView;
    }

    public static void juntar(String cantidad, String nombre, String idIngre, String totalPu, String umedida, String clasificacionIngredientes, String tipo, String can, ArrayList<String> lnombre, ArrayList<String> subLAuxUmedida, ArrayList<String> subLAuxCan, ArrayList<String> subLAuxClasificacionIngredientes, ArrayList<String> subLAuxTipo, ArrayList<String> subLPrecioModificable, ArrayList<String> subLAuxCantidad, ArrayList<String> subLAuxIdIngre) {
        int i;
        for (i = 0; i < LAuxNombre.size(); i++) {
            if (((String) LAuxNombre.get(i)).equals(nombre)) {
                LAuxUmedida.remove(i);
                LAuxCan.remove(i);
                LAuxClasificacionIngredientes.remove(i);
                LAuxTipo.remove(i);
                LPrecioModificable.remove(i);
                LAuxNombre.remove(i);
                LAuxCantidad.remove(i);
                LAuxIdIngre.remove(i);
            }
        }
        if (lnombre == null) {
            unoNombre.add(null + "°" + porno);
            unoUmedida.add(null);
            unoCan.add(null);
            unoClasificacionIngredientes.add(null);
            unoTipo.add(null);
            unoPrecioModificable.add(null);
            unoCantidad.add(null);
            unoIdIngre.add(null);
        } else {
            for (int y = 0; y < lnombre.size(); y++) {
                unoNombre.add(((String) lnombre.get(y)) + "°" + porno);
                unoUmedida.add(subLAuxUmedida.get(y));
                unoCan.add(subLAuxCan.get(y));
                unoClasificacionIngredientes.add(subLAuxClasificacionIngredientes.get(y));
                unoTipo.add(subLAuxTipo.get(y));
                unoPrecioModificable.add(subLPrecioModificable.get(y));
                unoCantidad.add(subLAuxCantidad.get(y));
                unoIdIngre.add(subLAuxIdIngre.get(y));
            }
        }
        porno++;
        LAuxUmedida.add(umedida);
        LAuxClasificacionIngredientes.add(clasificacionIngredientes);
        LAuxTipo.add(tipo);
        LAuxCan.add(can);
        LAuxNombre.add(nombre);
        LPrecioModificable.add(totalPu);
        LAuxCantidad.add(cantidad);
        LAuxIdIngre.add(idIngre);
        for (i = 0; i < LAuxNombre.size(); i++) {
            if (((String) LAuxCantidad.get(i)).equals("0")) {
                LAuxUmedida.remove(i);
                LAuxCan.remove(i);
                LAuxClasificacionIngredientes.remove(i);
                LAuxTipo.remove(i);
                LPrecioModificable.remove(i);
                LAuxNombre.remove(i);
                LAuxCantidad.remove(i);
                LAuxIdIngre.remove(i);
            }
        }
    }

    public void f_agre_Dism_ingre() {
        if (LAuxNombre.isEmpty()) {
            Toast.makeText(rootView.getContext(), "Lista de productos vacia", Toast.LENGTH_LONG).show();
        }
        if (!LAuxNombre.isEmpty()) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
            myRef.child("idMesero").setValue(Login.uidUsuario);
            myRef.child("nombreMesero").setValue(Login.name);
            myRef.child("mesa").setValue(Mesa.idMesaSel);
            myRef.child("nombre").setValue(Mesa.nomMesa);
            AdaptadorPlatillos.value++;
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("clasificacionMenu").setValue(fragmentoCategorias.selCategoria);
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("nombre").setValue(SelNombre);
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("codigoBarras").setValue(SelIdPlatillo);
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("estatus").setValue("Agregado");
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("clasificacionRecurso").setValue(selIdClasificacion);
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("tipo").setValue(tipo);
            double SlPrec = Double.parseDouble(SelPrecio);
            int y = LAuxTipo.size() - 1;
            for (int i = 0; i < LAuxNombre.size(); i++) {
                String accion = ((!((String) LPrecioModificable.get(i)).equals("") || ((String) LPrecioModificable.get(i)).equals("NaN")) && Double.parseDouble((String) LPrecioModificable.get(i)) != Double.NaN) ? null : null;
                if (((String) LAuxCantidad.get(i)).equals("0")) {
                    accion = "Eliminar";
                } else if (!((String) LAuxCantidad.get(i)).equals("0")) {
                    accion = "Agregar";
                }
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("umedida").setValue(LAuxUmedida.get(i));
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("clasificacionIngrediente").setValue(LAuxClasificacionIngredientes.get(i));
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("tipo").setValue(LAuxTipo.get(i));
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("accion").setValue(accion);
                f_Agregar_Destinos((String) LAuxClasificacionIngredientes.get(y), (String) LAuxTipo.get(y), (String) LAuxIdIngre.get(y), AdaptadorPlatillos.value + "", myRef);
                y--;
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("nombre").setValue(LAuxNombre.get(i));
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("cantidad").setValue(LAuxCan.get(i));
                myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("estatus").setValue("Agregado");
                for (int l = 0; l < unoNombre.size(); l++) {
                    String[] a = ((String) unoNombre.get(l)).split("°");
                    if (a[1].equals(i + "")) {
                        if (a[0].equals("null")) {
                            a[0] = null;
                        }
                        myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("xmodificados").child(((String) unoIdIngre.get(l)) + "").child("nombre").setValue(a[0]);
                        myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("xmodificados").child(((String) unoIdIngre.get(l)) + "").child("umedida").setValue(unoUmedida.get(l));
                        myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("xmodificados").child(((String) unoIdIngre.get(l)) + "").child("cantidad").setValue(unoCan.get(l));
                        myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("xmodificados").child(((String) unoIdIngre.get(l)) + "").child("clasificacionIngredientes").setValue(unoClasificacionIngredientes.get(l));
                        myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("xmodificados").child(((String) unoIdIngre.get(l)) + "").child("tipo").setValue(unoTipo.get(l));
                        myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("xmodificados").child(((String) unoIdIngre.get(l)) + "").child("precio").setValue(unoPrecioModificable.get(l));
                    }
                }
            }
            myRef.child("Lista").child("count").setValue(AdaptadorPlatillos.value + "");
            myRef.child("Lista").child(AdaptadorPlatillos.value + "").child("precio").setValue(SlPrec + "");
            AdaptadorPlatillos.value++;
            Carta.tvPaquetes.dismiss();
            fragmentoCompras.f_Saber_Precio1();
            LAuxUmedida.clear();
            LAuxClasificacionIngredientes.clear();
            LAuxTipo.clear();
            LAuxCan.clear();
            unoNombre.clear();
            unoUmedida.clear();
            unoCan.clear();
            unoCantidad.clear();
            unoClasificacionIngredientes.clear();
            unoTipo.clear();
            unoPrecioModificable.clear();
            unoIdIngre.clear();
            porno = 0;
            LAuxNombre.clear();
            LPrecioModificable.clear();
            LAuxCantidad.clear();
            LAuxIdIngre.clear();
            Toast.makeText(rootView.getContext(), "Agregado Exitosamente", Toast.LENGTH_LONG).show();
        }
    }

    private void f_Agregar_Destinos(String CIngredientes, String tipo, final String IDIngre, final String val, final DatabaseReference myRef) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(CIngredientes).child(tipo).child(IDIngre).child("destino").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = 23)
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("Lista").child(val).child("productos").child(IDIngre).child("destino").setValue(String.valueOf(dataSnapshot.getValue()));
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void f_Consulta_hijos_Radio() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(selIdClasificacion).child(tipo).child(SelIdPlatillo).child("ingredientes").addValueEventListener(new C07364());
    }

    @RequiresApi(api = 23)
    public void f_Llenar_Recycler_RadioButton(ArrayList<String> LIngredientes, int su) {
        ArrayList<String> nombres = new ArrayList();
        ArrayList<String> cantidades = new ArrayList();
        ArrayList<String> niveles = new ArrayList();
        ArrayList<String> clasificacionIngredientes = new ArrayList();
        ArrayList<String> tipos = new ArrayList();
        ArrayList<String> umedidas = new ArrayList();
        ArrayList<String> idIngres = new ArrayList();
        Builder builder = new Builder(getContext());
        for (int i = 0; i <= LIngredientes.size() - 1; i++) {
            String[] aux = ((String) LIngredientes.get(i)).split("°");
            nombres.add(aux[0]);
            cantidades.add(aux[1]);
            clasificacionIngredientes.add(aux[2]);
            tipos.add(aux[3]);
            umedidas.add(aux[4]);
            idIngres.add(aux[5]);
            niveles.add(aux[6]);
        }
        builder.setTitle("Nivel " + su + "");
        String[] nombresA = (String[]) nombres.toArray(new String[nombres.size()]);
        final String[] finalNombresA = nombresA;
        final String[] finalCantidadesA = (String[]) cantidades.toArray(new String[cantidades.size()]);
        final String[] finalClasificacionIngredientesA = (String[]) clasificacionIngredientes.toArray(new String[clasificacionIngredientes.size()]);
        final String[] finalTiposA = (String[]) tipos.toArray(new String[tipos.size()]);
        final String[] finalUmedidasA = (String[]) umedidas.toArray(new String[umedidas.size()]);
        final String[] finalIdIngresA = (String[]) idIngres.toArray(new String[idIngres.size()]);
        final String[] finalNivelesA = (String[]) niveles.toArray(new String[niveles.size()]);
        builder.setSingleChoiceItems((CharSequence[]) nombresA, 0, new DialogInterface.OnClickListener() {
            @RequiresApi(api = 23)
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(paquetes.this.getContext(), finalNombresA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(paquetes.this.getContext(), finalCantidadesA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(paquetes.this.getContext(), finalClasificacionIngredientesA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(paquetes.this.getContext(), finalTiposA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(paquetes.this.getContext(), finalUmedidasA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(paquetes.this.getContext(), finalIdIngresA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(paquetes.this.getContext(), finalNivelesA[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton((CharSequence) "OK", new C02986());
        builder.setNegativeButton((CharSequence) "Cancel", null);
        builder.create().show();
        LIngredientes.clear();
    }

    public void f_Llenar_Recycler_Frag(ArrayList<String> LIngredientes) {
        int i;
        double sumatoria = 0.0d;
        for (i = 0; i <= LIngredientes.size() - 1; i++) {
            String[] aux = ((String) LIngredientes.get(i)).split("°");
            double unidBase = 0.0d;
            String cantidad = aux[1];
            String umedida = aux[4];
            double cant = Double.parseDouble(cantidad);
            if (umedida.equals("gr")) {
                unidBase = cant * 0.001d;
            }
            if (umedida.equals("ml")) {
                unidBase = cant / 1000.0d;
            }
            if (umedida.equals("oz")) {
                unidBase = cant / 33.814d;
            }
            sumatoria += unidBase;
            String str = "";
        }
        for (i = 0; i <= LIngredientes.size() - 1; i++) {
            double   unidBase = 0.0d;
            String[] aux = ((String) LIngredientes.get(i)).split("°");
            String nombre = aux[0];
            String  cantidad = aux[1];
            String clasificacionIngrediente = aux[2];
            String tipo = aux[3];
            String  umedida = aux[4];
            String idIngre = aux[5];
            String nivel = aux[6];
            double cant = Double.parseDouble(cantidad);
            if (umedida.equals("gr")) {
                unidBase = cant * 0.001d;
            }
            if (umedida.equals("ml")) {
                unidBase = cant / 1000.0d;
            }
            if (umedida.equals("oz")) {
                unidBase = cant / 33.814d;
            }
            double preUnitario = (unidBase * Double.parseDouble(SelPrecio)) / sumatoria;
            colorsSub.add(new ConstructorPaquetes(nombre, cantidad, this.f12c + "", clasificacionIngrediente, tipo, umedida, idIngre, preUnitario + "", nivel + "", ""));
            this.variable++;
        }
        recyclerViewSub = (RecyclerView) rootView.findViewById(R.id.recyclerViewIngr);
        recyclerViewSub.clearOnScrollListeners();
        recyclerViewSub.setAdapter(new AdaptadorPaquetes(colorsSub, new C07377(), this.variable, rootView.getContext()));
        recyclerViewSub.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        LIngredientes.clear();
    }

    private void f_agre_Dism_Paquetes() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
        myRef.child("idMesero").setValue(Login.uidUsuario);
        myRef.child("nombreMesero").setValue(Login.name);
        myRef.child("mesa").setValue(Mesa.idMesaSel);
        myRef.child("nombre").setValue(Mesa.nomMesa);
        AdaptadorPlatillos.value++;
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("clasificacionMenu").setValue(fragmentoCategorias.selCategoria);
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("nombre").setValue(SelNombre);
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("codigoBarras").setValue(SelIdPlatillo);
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("estatus").setValue("Agregado");
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("clasificacionRecurso").setValue(selIdClasificacion);
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("tipo").setValue(tipo);
        double SlPrec = Double.parseDouble(SelPrecio);
        for (int i = 0; i < LAuxNombre.size(); i++) {
            if (!((String) LPrecioModificable.get(i)).equals("")) {
                SlPrec += Double.parseDouble((String) LPrecioModificable.get(i));
            }
            String accion = null;
            if (((String) LAuxCantidad.get(i)).equals("0")) {
                accion = "Eliminar";
            } else if (!((String) LAuxCantidad.get(i)).equals("0")) {
                accion = "Agregar";
            }
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("nombre").setValue(LAuxNombre.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("cantidad").setValue(LAuxCantidad.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("productos").child((String) LAuxIdIngre.get(i)).child("accion").setValue(accion);
        }
        myRef.child("Lista").child("count").setValue(AdaptadorPlatillos.value + "");
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("precio").setValue(SlPrec + "");
        AdaptadorPlatillos.value++;
        Carta.tvIng.dismiss();
        fragmentoCompras.f_Saber_Precio1();
    }
}
