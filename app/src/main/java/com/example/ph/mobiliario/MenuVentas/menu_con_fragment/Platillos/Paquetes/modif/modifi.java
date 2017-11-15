package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes.modif;

import android.app.DialogFragment;
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
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes.paquetes;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras.fragmentoCompras;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class modifi extends DialogFragment {
    static String SelDestino;
    static String SelIdPlatillo;
    static String SelNombre;
    public static String SelPrecio;
    static String Selcontador;
    public static ArrayList<String> SubLAuxCan = new ArrayList();
    public static ArrayList<String> SubLAuxCantidad = new ArrayList();
    public static ArrayList<String> SubLAuxClasificacionIngredientes = new ArrayList();
    public static ArrayList<String> SubLAuxIdIngre = new ArrayList();
    public static ArrayList<String> SubLAuxNombre = new ArrayList();
    public static ArrayList<String> SubLAuxTipo = new ArrayList();
    public static ArrayList<String> SubLAuxUmedida = new ArrayList();
    public static ArrayList<String> SubLPrecioModificable = new ArrayList();
    private static List<ConstructorModifi> colorsModifi;
    public static ArrayList<String> nombrexxx = new ArrayList();
    static RecyclerView recyclerViewModifi;
    static View rootView;
    static String selIdClasificacion;
    static String selprecio;
    static String selumedida;
    static String tipo;
    ArrayList<String> LIngredientes = new ArrayList();
    SharedPreferences Modpref;
    Button btnEditarIngredientes;
    Button btnEditarIngredientes2;
    int f11c;
    int variable;

    class C02911 implements OnClickListener {
        C02911() {
        }

        public void onClick(View view) {
            modifi.this.f_agre_Dism_ingre();
        }
    }

    class C02922 implements OnClickListener {
        C02922() {
        }

        public void onClick(View view) {
            modifi.this.f_agre_Dism_ingre();
        }
    }

    class C02946 implements DialogInterface.OnClickListener {
        C02946() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C07334 implements ValueEventListener {
        C07334() {
        }

        @RequiresApi(api = 23)
        public void onDataChange(DataSnapshot dataSnapshot) {
            int su;
            modifi.colorsModifi.clear();
            modifi.SubLAuxNombre.clear();
            modifi.SubLAuxCantidad.clear();
            modifi.SubLAuxIdIngre.clear();
            modifi.this.LIngredientes.clear();
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
            Editor editor = modifi.this.Modpref.edit();
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
                        modifi.this.LIngredientes.add(nombre + "°" + cantidad + "°" + clasificacionIngrediente + "°" + tipo + "°" + umedida + "°" + id + "°" + nivel);
                    }
                }
                modifi.this.f_Llenar_Recycler_Frag(modifi.this.LIngredientes);
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Log.w("tag", "loadPost:onCancelled", databaseError.toException());
        }
    }

    class C07347 implements ClickeadorModifi {
        C07347() {
        }

        public void onClick(View v, int position) {
        }
    }

    public modifi(String selIdClasificacion, String tipo, String Selnombre, String SelPrecio, String SelIdPlatillo, String SelDestino, String Selcontador, String selprecio, String selumedida) {
        this.selIdClasificacion = selIdClasificacion;
        this.tipo = tipo;
        this.SelNombre = Selnombre;
        this.SelPrecio = SelPrecio;
        this.SelIdPlatillo = SelIdPlatillo;
        this.SelDestino = SelDestino;
        this.Selcontador = Selcontador;
        this.selprecio = selprecio;
        this.selumedida = selumedida;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_modifi, container);
        this.btnEditarIngredientes2 = (Button) rootView.findViewById(R.id.btnEditarIngredientes2);
        this.btnEditarIngredientes = (Button) rootView.findViewById(R.id.btnEditarIngredientes);
        colorsModifi = new ArrayList();
        f_Consulta_hijos_Radio();
        this.btnEditarIngredientes.setOnClickListener(new C02911());
        this.btnEditarIngredientes2.setOnClickListener(new C02922());
        this.Modpref = rootView.getContext().getSharedPreferences("Moddata", 0);
        getDialog().setTitle("Elementos");
        return rootView;
    }

    public static void juntar(String cantidad, String nombre, String idIngre, String totalPu, String umedida, String clasificacionIngredientes, String tipo, String can) {
        int i;
        for (i = 0; i < SubLAuxNombre.size(); i++) {
            if (((String) SubLAuxNombre.get(i)).equals(nombre)) {
                SubLAuxUmedida.remove(i);
                SubLAuxCan.remove(i);
                SubLAuxClasificacionIngredientes.remove(i);
                SubLAuxTipo.remove(i);
                SubLPrecioModificable.remove(i);
                SubLAuxNombre.remove(i);
                SubLAuxCantidad.remove(i);
                SubLAuxIdIngre.remove(i);
            }
        }
        SubLAuxUmedida.add(umedida);
        SubLAuxClasificacionIngredientes.add(clasificacionIngredientes);
        SubLAuxTipo.add(tipo);
        SubLAuxCan.add(can);
        SubLAuxNombre.add(nombre);
        SubLPrecioModificable.add(totalPu);
        SubLAuxCantidad.add(cantidad);
        SubLAuxIdIngre.add(idIngre);
        for (i = 0; i < SubLAuxNombre.size(); i++) {
            if (((String) SubLAuxCantidad.get(i)).equals("0")) {
                SubLAuxUmedida.remove(i);
                SubLAuxCan.remove(i);
                SubLAuxClasificacionIngredientes.remove(i);
                SubLAuxTipo.remove(i);
                SubLPrecioModificable.remove(i);
                SubLAuxNombre.remove(i);
                SubLAuxCantidad.remove(i);
                SubLAuxIdIngre.remove(i);
            }
        }
    }

    public void f_agre_Dism_ingre() {
        if (SubLAuxNombre.isEmpty()) {
            Toast.makeText(rootView.getContext(), "Lista de productos vacia", Toast.LENGTH_LONG).show();
        }
        if (!SubLAuxNombre.isEmpty()) {
            paquetes.juntar(Selcontador, SelNombre, SelIdPlatillo, selprecio + "", selumedida, selIdClasificacion, tipo, "1", SubLAuxNombre, SubLAuxUmedida, SubLAuxCan, SubLAuxClasificacionIngredientes, SubLAuxTipo, SubLPrecioModificable, SubLAuxCantidad, SubLAuxIdIngre);
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
            Carta.tvModifi.dismiss();
            SubLAuxUmedida.clear();
            SubLAuxClasificacionIngredientes.clear();
            SubLAuxTipo.clear();
            SubLAuxCan.clear();
            SubLAuxNombre.clear();
            SubLPrecioModificable.clear();
            SubLAuxCantidad.clear();
            SubLAuxIdIngre.clear();
            Toast.makeText(rootView.getContext(), "Agregado Exitosamente", Toast.LENGTH_LONG).show();
        }
    }

    private String f_Agregar_Destinos(String CIngredientes, String tipo, String IDIngre, String val, DatabaseReference myRef) {
        final String[] u = new String[1];
        final DatabaseReference databaseReference = myRef;
        final String str = val;
        final String str2 = IDIngre;
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(CIngredientes).child(tipo).child(IDIngre).child("destino").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = 23)
            public void onDataChange(DataSnapshot dataSnapshot) {
                u[0] = String.valueOf(dataSnapshot.getValue());
                databaseReference.child("Lista").child(str).child("productos").child(str2).child("destino").setValue(u[0]);
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
        return u[0];
    }

    private void f_Consulta_hijos_Radio() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(selIdClasificacion).child(tipo).child(SelIdPlatillo).child("ingredientes").addValueEventListener(new C07334());
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
                Toast.makeText(modifi.this.getContext(), finalNombresA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(modifi.this.getContext(), finalCantidadesA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(modifi.this.getContext(), finalClasificacionIngredientesA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(modifi.this.getContext(), finalTiposA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(modifi.this.getContext(), finalUmedidasA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(modifi.this.getContext(), finalIdIngresA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(modifi.this.getContext(), finalNivelesA[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton((CharSequence) "OK", new C02946());
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
            double unidBase = 0.0d;
            String[]  aux = ((String) LIngredientes.get(i)).split("°");
            String nombre = aux[0];
            String cantidad = aux[1];
            String clasificacionIngrediente = aux[2];
            String tipo = aux[3];
            String  umedida = aux[4];
            String idIngre = aux[5];
            String nivel = aux[6];
            double  cant = Double.parseDouble(cantidad);
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
            colorsModifi.add(new ConstructorModifi(nombre, cantidad, this.f11c + "", clasificacionIngrediente, tipo, umedida, idIngre, preUnitario + "", nivel + "", ""));
            this.variable++;
        }
        recyclerViewModifi = (RecyclerView) rootView.findViewById(R.id.recyclerViewModifi);
        recyclerViewModifi.clearOnScrollListeners();
        recyclerViewModifi.setAdapter(new AdaptadorModifi(colorsModifi, new C07347(), this.variable, rootView.getContext()));
        recyclerViewModifi.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
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
        for (int i = 0; i < SubLAuxNombre.size(); i++) {
            if (!((String) SubLPrecioModificable.get(i)).equals("")) {
                SlPrec += Double.parseDouble((String) SubLPrecioModificable.get(i));
            }
            String accion = null;
            if (((String) SubLAuxCantidad.get(i)).equals("0")) {
                accion = "Eliminar";
            } else if (!((String) SubLAuxCantidad.get(i)).equals("0")) {
                accion = "Agregar";
            }
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("productos").child((String) SubLAuxIdIngre.get(i)).child("nombre").setValue(SubLAuxNombre.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("productos").child((String) SubLAuxIdIngre.get(i)).child("cantidad").setValue(SubLAuxCantidad.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("productos").child((String) SubLAuxIdIngre.get(i)).child("accion").setValue(accion);
        }
        myRef.child("Lista").child("count").setValue(AdaptadorPlatillos.value + "");
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("precio").setValue(SlPrec + "");
        AdaptadorPlatillos.value++;
        Carta.tvIng.dismiss();
        fragmentoCompras.f_Saber_Precio1();
    }
}

