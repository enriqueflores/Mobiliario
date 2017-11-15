package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Sub;

import android.app.DialogFragment;
import android.content.DialogInterface;
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
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras.fragmentoCompras;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Ingr extends DialogFragment {
    public static ArrayList<String> LAuxCantidad = new ArrayList();
    public static ArrayList<String> LAuxIdIngre = new ArrayList();
    public static ArrayList<String> LAuxNombre = new ArrayList();
    public static ArrayList<String> LPrecioModificable = new ArrayList();
    static String SelDestino;
    static String SelIdPlatillo;
    static String SelNombre;
    public static String SelPrecio;
    private static List<ConstructorSub> colorsSub;
    static RecyclerView recyclerViewSub;
    static View rootView;
    static String selIdClasificacion;
    static String tipo;
    ArrayList<String> LIngredientes = new ArrayList();
    Button btnEditarIngredientes;
    Button btnEditarIngredientes2;
    int f13c;

    class C03011 implements OnClickListener {
        C03011() {
        }

        public void onClick(View view) {
            Ingr.this.f_agre_Dism_ingre();
        }
    }

    class C03022 implements OnClickListener {
        C03022() {
        }

        public void onClick(View view) {
            Ingr.this.f_agre_Dism_ingre();
        }
    }

    class C03046 implements DialogInterface.OnClickListener {
        C03046() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C07383 implements ValueEventListener {
        C07383() {
        }

        @RequiresApi(api = 23)
        public void onDataChange(DataSnapshot dataSnapshot) {
            Ingr.colorsSub.clear();
            Ingr.LAuxNombre.clear();
            Ingr.LAuxCantidad.clear();
            Ingr.LAuxIdIngre.clear();
            Ingr.this.LIngredientes.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String id = postSnapshot.getKey();
                int count = (int) dataSnapshot.getChildrenCount();
                String nombre = String.valueOf(postSnapshot.child("nombre").getValue());
                String cantidad = String.valueOf(postSnapshot.child("cantidad").getValue());
                String clasificacionIngrediente = String.valueOf(postSnapshot.child("clasificacionIngrediente").getValue());
                String tipo = String.valueOf(postSnapshot.child("tipo").getValue());
                String umedida = String.valueOf(postSnapshot.child("umedida").getValue());
                String nivel = String.valueOf(postSnapshot.child("nivel").getValue());
                int nivelConsulta = Integer.parseInt(nivel);
                Ingr.this.LIngredientes.add(nombre + "°" + cantidad + "°" + clasificacionIngrediente + "°" + tipo + "°" + umedida + "°" + id + "°" + nivel);
            }
            Ingr.this.f_Llenar_Recycler_Frag(Ingr.this.LIngredientes);
        }

        public void onCancelled(DatabaseError databaseError) {
            Log.w("tag", "loadPost:onCancelled", databaseError.toException());
        }
    }

    class C07394 implements ValueEventListener {
        C07394() {
        }

        @RequiresApi(api = 23)
        public void onDataChange(DataSnapshot dataSnapshot) {
            Ingr.colorsSub.clear();
            Ingr.LAuxNombre.clear();
            Ingr.LAuxCantidad.clear();
            Ingr.LAuxIdIngre.clear();
            Ingr.this.LIngredientes.clear();
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
            for (int su = nivelesAUX.size(); su > 0; su--) {
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
                        Ingr.this.LIngredientes.add(nombre + "°" + cantidad + "°" + clasificacionIngrediente + "°" + tipo + "°" + umedida + "°" + id + "°" + nivel);
                    }
                }
                Ingr.this.f_Llenar_Recycler_RadioButton(Ingr.this.LIngredientes, su);
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Log.w("tag", "loadPost:onCancelled", databaseError.toException());
        }
    }

    class C07407 implements ClickeadorSub {
        C07407() {
        }

        public void onClick(View v, int position) {
            Toast.makeText(Ingr.rootView.getContext(), String.valueOf(((ConstructorSub) Ingr.colorsSub.get(position)).getNombreIngrediente()) + "  posicion: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public Ingr(String selIdClasificacion, String tipo, String Selnombre, String SelPrecio, String SelIdPlatillo, String SelDestino) {
        selIdClasificacion = selIdClasificacion;
        tipo = tipo;
        SelNombre = Selnombre;
        SelPrecio = SelPrecio;
        SelIdPlatillo = SelIdPlatillo;
        SelDestino = SelDestino;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ingredientes, container);
        this.btnEditarIngredientes2 = (Button) rootView.findViewById(R.id.btnEditarIngredientes2);
        this.btnEditarIngredientes = (Button) rootView.findViewById(R.id.btnEditarIngredientes);
        colorsSub = new ArrayList();
        f_Consulta_hijos();
        this.btnEditarIngredientes.setOnClickListener(new C03011());
        this.btnEditarIngredientes2.setOnClickListener(new C03022());
        getDialog().setTitle("Submenu");
        return rootView;
    }

    public static void juntar(String cantidad, String nombre, String idIngre, String totalPu) {
        for (int i = 0; i < LAuxNombre.size(); i++) {
            if (((String) LAuxNombre.get(i)).equals(nombre)) {
                LPrecioModificable.remove(i);
                LAuxNombre.remove(i);
                LAuxCantidad.remove(i);
                LAuxIdIngre.remove(i);
            }
        }
        LAuxNombre.add(nombre);
        LPrecioModificable.add(totalPu);
        LAuxCantidad.add(cantidad);
        LAuxIdIngre.add(idIngre);
    }

    public void f_agre_Dism_ingre() {
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
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("modificado").child((String) LAuxIdIngre.get(i)).child("nombre").setValue(LAuxNombre.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("modificado").child((String) LAuxIdIngre.get(i)).child("cantidad").setValue(LAuxCantidad.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("modificado").child((String) LAuxIdIngre.get(i)).child("accion").setValue(accion);
        }
        myRef.child("Lista").child("count").setValue(AdaptadorPlatillos.value + "");
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("precio").setValue(SlPrec + "");
        AdaptadorPlatillos.value++;
        Carta.tvIng.dismiss();
        fragmentoCompras.f_Saber_Precio1();
    }

    private void f_Consulta_hijos() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(selIdClasificacion).child(tipo).child(SelIdPlatillo).child("ingredientes").addValueEventListener(new C07383());
    }

    private void f_Consulta_hijos_Radio() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("recursos").child(selIdClasificacion).child(tipo).child(SelIdPlatillo).child("ingredientes").addValueEventListener(new C07394());
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
                Toast.makeText(Ingr.this.getContext(), finalNombresA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(Ingr.this.getContext(), finalCantidadesA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(Ingr.this.getContext(), finalClasificacionIngredientesA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(Ingr.this.getContext(), finalTiposA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(Ingr.this.getContext(), finalUmedidasA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(Ingr.this.getContext(), finalIdIngresA[which], Toast.LENGTH_SHORT).show();
                Toast.makeText(Ingr.this.getContext(), finalNivelesA[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton((CharSequence) "OK", new C03046());
        builder.setNegativeButton((CharSequence) "Cancel", null);
        builder.create().show();
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
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("modificado").child((String) LAuxIdIngre.get(i)).child("nombre").setValue(LAuxNombre.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("modificado").child((String) LAuxIdIngre.get(i)).child("cantidad").setValue(LAuxCantidad.get(i));
            myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("modificado").child((String) LAuxIdIngre.get(i)).child("accion").setValue(accion);
        }
        myRef.child("Lista").child("count").setValue(AdaptadorPlatillos.value + "");
        myRef.child("Lista").child(SelDestino).child(AdaptadorPlatillos.value + "").child("precio").setValue(SlPrec + "");
        AdaptadorPlatillos.value++;
        Carta.tvIng.dismiss();
        fragmentoCompras.f_Saber_Precio1();
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
            String[] aux = ((String) LIngredientes.get(i)).split("°");
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
            colorsSub.add(new ConstructorSub(nombre, cantidad, this.f13c + "", clasificacionIngrediente, tipo, umedida, idIngre, preUnitario + "", nivel + ""));
        }
        recyclerViewSub = (RecyclerView) rootView.findViewById(R.id.recyclerViewIngr);
        recyclerViewSub.clearOnScrollListeners();
        recyclerViewSub.setAdapter(new AdaptadorSub(colorsSub, new C07407()));
        recyclerViewSub.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    }
}

