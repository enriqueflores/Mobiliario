package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.fragmentoPlatillos;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class fragmentoCompras extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<ConstructorCompras> colorsCompras;
    static RecyclerView recyclerViewCompras;
    public static String saberSiTodosSonEntregados;
    ArrayList<String> LDestinos = new ArrayList();
    ArrayList<String> LEstatus = new ArrayList();
    ArrayList<String> LProductos = new ArrayList();
    ArrayList<String> LTrans = new ArrayList();
    int clein;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    class C03141 implements OnClickListener {
        C03141() {
        }

        public void onClick(View view) {
            fragmentoCompras.this.f_Cambiar_estatus_enviado_1();
            Toast.makeText(fragmentoCompras.this.getContext(), "Enviado", Toast.LENGTH_LONG).show();
        }
    }

    class C03152 implements OnClickListener {
        C03152() {
        }

        public void onClick(View view) {
            if (fragmentoCompras.colorsCompras.isEmpty()) {
                Toast.makeText(fragmentoCompras.this.getContext(), "No existen elementos", Toast.LENGTH_SHORT).show();
            } else if (fragmentoCompras.saberSiTodosSonEntregados.equals("listo")) {
                fragmentoCompras.this.f_desifrar_idComanda();
                fragmentoCompras.this.clein = 0;
                fragmentoCompras.this.f_saber_lista();
            } else {
                Toast.makeText(fragmentoCompras.this.getContext(), "Existen elementos No entregados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class C03163 implements OnClickListener {
        C03163() {
        }

        public void onClick(View view) {
            if (fragmentoCompras.colorsCompras.isEmpty()) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
                fragmentoCompras.this.getActivity().finish();
                myRef.setValue(null);
                Toast.makeText(fragmentoCompras.this.getContext(), "Mesa Liberada", Toast.LENGTH_LONG).show();
            } else if (!fragmentoCompras.colorsCompras.isEmpty()) {
                Toast.makeText(fragmentoCompras.this.getContext(), "Existen platillos pendientes...", Toast.LENGTH_LONG).show();
            }
        }
    }

    class C03174 implements OnClickListener {
        C03174() {
        }

        public void onClick(View view) {
            Toast.makeText(fragmentoCompras.this.getContext(), "Imprimir", Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class C07445 implements ValueEventListener {
        C07445() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            fragmentoCompras.this.LTrans.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String xxx = String.valueOf(postSnapshot.getKey());
                if (!xxx.equals("count")) {
                    String tipo = String.valueOf(postSnapshot.child("tipo").getValue());
                    String precio = String.valueOf(postSnapshot.child("precio").getValue());
                    String nombre = String.valueOf(postSnapshot.child("nombre").getValue());
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    String codigoBarras = String.valueOf(postSnapshot.child("codigoBarras").getValue());
                    String clasificacionRecurso = String.valueOf(postSnapshot.child("clasificacionRecurso").getValue());
                    String clasificacionMenu = String.valueOf(postSnapshot.child("clasificacionMenu").getValue());
                    String destino = String.valueOf(postSnapshot.child("destino").getValue());
                    fragmentoCompras.this.LTrans.add(tipo + "°" + precio + "°" + nombre + "°" + estatus + "°" + codigoBarras + "°" + clasificacionMenu + "°" + clasificacionRecurso + "°" + xxx + "°" + destino + "°" + String.valueOf(postSnapshot.child("productos").getValue()));
                    fragmentoCompras.this.productos(xxx, fragmentoCompras.this.LTrans);
                }
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07467 implements ValueEventListener {
        C07467() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String idCom = String.valueOf(postSnapshot.getKey());
                if (idCom.equals(Mesa.idComanda)) {
                    fragmentoCompras.this.f_escribir_pagos(idCom, String.valueOf(postSnapshot.child("idMesero").getValue()), String.valueOf(postSnapshot.child("mesa").getValue()), String.valueOf(postSnapshot.child("nombre").getValue()), String.valueOf(postSnapshot.child("nombreMesero").getValue()), String.valueOf(postSnapshot.child("total").getValue()));
                }
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    static class C07478 implements ValueEventListener {
        C07478() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Carta.total = 0.0d;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String PPprecio = (String) postSnapshot.child("precio").getValue();
                if (!postSnapshot.getKey().equals("count")) {
                    Carta.total += Double.parseDouble(PPprecio);
                }
            }
            Carta.btnfr3.setText("$ " + String.format("%.2f", new Object[]{Double.valueOf(Carta.total)}));
            DatabaseReference myRefxxx = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
            if (Carta.total != 0.0d) {
                myRefxxx.child("total").setValue(String.format("%.2f", new Object[]{Double.valueOf(Carta.total)}));
            }
        }

        public void onCancelled(DatabaseError databaseError) {
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
        View v = inflater.inflate(R.layout.fragment_fr, container, false);
        colorsCompras = new ArrayList();
        Carta.comtras = "si";
        ((Button) v.findViewById(R.id.fabEstatusEnviado)).setOnClickListener(new C03141());
        ((Button) v.findViewById(R.id.fabCobrar)).setOnClickListener(new C03152());
        ((Button) v.findViewById(R.id.fabLiberar)).setOnClickListener(new C03163());
        ((Button) v.findViewById(R.id.fabImprimir)).setOnClickListener(new C03174());
        f_Saber_Destinos(v);
        f_Cambiar_estatus_LISTO_1();
        f_Saber_Precio1();
        f_Cambiar_estatus_Entregado_1();
        return v;
    }

    private void f_saber_lista() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").addListenerForSingleValueEvent(new C07445());
    }

    public void productos(String xxx, final ArrayList<String> LTrans) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").child(xxx).child("productos").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                String verdad = String.valueOf(dataSnapshot.exists());
                fragmentoCompras com_example_ph_mobiliario_MenuVentas_menu_con_fragment_compras_fragmentoCompras;
                if (verdad.equals("false")) {
                    com_example_ph_mobiliario_MenuVentas_menu_con_fragment_compras_fragmentoCompras = fragmentoCompras.this;
                    com_example_ph_mobiliario_MenuVentas_menu_con_fragment_compras_fragmentoCompras.clein++;
                } else if (verdad.equals("true")) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String id = String.valueOf(postSnapshot.getKey());
                        String accion = String.valueOf(postSnapshot.child("accion").getValue());
                        String cantidad = String.valueOf(postSnapshot.child("cantidad").getValue());
                        String clasificacionIngredientes = String.valueOf(postSnapshot.child("clasificacionIngrediente").getValue());
                        String destino = String.valueOf(postSnapshot.child("destino").getValue());
                        String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                        String nombre = String.valueOf(postSnapshot.child("nombre").getValue());
                        String tipo = String.valueOf(postSnapshot.child("tipo").getValue());
                        String umedida = String.valueOf(postSnapshot.child("umedida").getValue());
                        fragmentoCompras.this.LProductos.add(id + "°" + accion + "°" + cantidad + "°" + clasificacionIngredientes + "°" + destino + "°" + estatus + "°" + nombre + "°" + tipo + "°" + umedida + "°" + String.valueOf(postSnapshot.child("xmodificados").getValue()) + "°" + fragmentoCompras.this.clein + "");
                    }
                    com_example_ph_mobiliario_MenuVentas_menu_con_fragment_compras_fragmentoCompras = fragmentoCompras.this;
                    com_example_ph_mobiliario_MenuVentas_menu_con_fragment_compras_fragmentoCompras.clein++;
                }
                fragmentoCompras.this.f_llenar(LTrans, fragmentoCompras.this.LProductos);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void f_eliminarComanda() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).removeValue();
    }

    private void f_llenar(ArrayList<String> lTrans, ArrayList<String> LProductos) {
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("porpagar").child(Mesa.idComanda).child("Lista");
        for (int i = 0; i <= lTrans.size() - 1; i++) {
            String[] aux = ((String) lTrans.get(i)).split("°");
            String tipo = aux[0];
            String precio = aux[1];
            String nombre = aux[2];
            String estatus = aux[3];
            String codigoBarras = aux[4];
            String clasificacionMenu = aux[5];
            String clasificacionRecurso = aux[6];
            String idPlat = aux[7];
            String destino = aux[8];
            if (destino.equals("null")) {
                destino = null;
            }
            myRef2.child(idPlat).child("tipo").setValue(tipo);
            myRef2.child(idPlat).child("precio").setValue(precio);
            myRef2.child(idPlat).child("nombre").setValue(nombre);
            myRef2.child(idPlat).child("estatus").setValue(estatus);
            myRef2.child(idPlat).child("codigoBarras").setValue(codigoBarras);
            myRef2.child(idPlat).child("clasificacionRecurso").setValue(clasificacionRecurso);
            myRef2.child(idPlat).child("clasificacionMenu").setValue(clasificacionMenu);
            myRef2.child(idPlat).child("destino").setValue(destino);
            if (LProductos != null) {
                for (int cont = 0; cont < LProductos.size(); cont++) {
                    String[] auxPro = ((String) LProductos.get(cont)).split("°");
                    if (auxPro[10].equals(i + "")) {
                        String idPro = auxPro[0];
                        String accion = auxPro[1];
                        String cantidad = auxPro[2];
                        String clasIngre = auxPro[3];
                        String destin = auxPro[4];
                        String estatu = auxPro[5];
                        String nomb = auxPro[6];
                        String tip = auxPro[7];
                        String umedi = auxPro[8];
                        String modificados = auxPro[9];
                        myRef2.child(idPlat).child("productos").child(idPro).child("nombre").setValue(nomb);
                        myRef2.child(idPlat).child("productos").child(idPro).child("destino").setValue(destin);
                        myRef2.child(idPlat).child("productos").child(idPro).child("clasificacionIngrediente").setValue(clasIngre);
                        myRef2.child(idPlat).child("productos").child(idPro).child("umedida").setValue(umedi);
                        myRef2.child(idPlat).child("productos").child(idPro).child("tipo").setValue(tip);
                        myRef2.child(idPlat).child("productos").child(idPro).child("cantidad").setValue(cantidad);
                        myRef2.child(idPlat).child("productos").child(idPro).child("estatus").setValue(estatu);
                        myRef2.child(idPlat).child("productos").child(idPro).child("accion").setValue(accion);
                        if (!modificados.equals("null")) {
                            String[] pro = modificados.replace("{", "").replace("}", "").replace("=", ",").split(",");
                            for (int vv = 0; vv < pro.length; vv += 13) {
                                myRef2.child(idPlat).child("productos").child(idPro).child("xmodificados").child(pro[vv]).child("nombre").setValue(pro[vv + 2]);
                                myRef2.child(idPlat).child("productos").child(idPro).child("xmodificados").child(pro[vv]).child("clasificacionIngrediente").setValue(pro[vv + 4]);
                                myRef2.child(idPlat).child("productos").child(idPro).child("xmodificados").child(pro[vv]).child("precio").setValue(pro[vv + 6]);
                                myRef2.child(idPlat).child("productos").child(idPro).child("xmodificados").child(pro[vv]).child("umedida").setValue(pro[vv + 8]);
                                myRef2.child(idPlat).child("productos").child(idPro).child("xmodificados").child(pro[vv]).child("tipo").setValue(pro[vv + 10]);
                                myRef2.child(idPlat).child("productos").child(idPro).child("xmodificados").child(pro[vv]).child("cantidad").setValue(pro[vv + 12]);
                            }
                        }
                    }
                }
            }
        }
        getActivity().finish();
        f_eliminarComanda();
    }

    private void f_desifrar_idComanda() {
        FirebaseDatabase.getInstance().getReference(Login.restaurante).child("listaCompras").addListenerForSingleValueEvent(new C07467());
    }

    private void f_escribir_pagos(String idCom, String idMesero, String mesa, String nombre, String nombreMesero, String stotal) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("porpagar").child(idCom);
        myRef.child("idMesero").setValue(idMesero);
        myRef.child("mesa").setValue(mesa);
        myRef.child("nombre").setValue(nombre);
        myRef.child("nombreMesero").setValue(nombreMesero);
        myRef.child("total").setValue(stotal);
    }

    public static void f_Saber_Precio1() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").addListenerForSingleValueEvent(new C07478());
    }

    private void f_Saber_Destinos(final View v) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                fragmentoCompras.this.LDestinos.clear();
                fragmentoCompras.saberSiTodosSonEntregados = "listo";
                String accion = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatilloPedido = postSnapshot.getKey();
                    if (!idPlatilloPedido.equals("count")) {
                        String PPnombre = (String) postSnapshot.child("nombre").getValue();
                        String PPclasificacionMenu = (String) postSnapshot.child("clasificacionMenu").getValue();
                        String PPclasificacionRecurso = String.valueOf(postSnapshot.child("clasificacionRecurso").getValue());
                        String PPcodigoBarras = (String) postSnapshot.child("codigoBarras").getValue();
                        String PPestatus = (String) postSnapshot.child("estatus").getValue();
                        String PPprecio = (String) postSnapshot.child("precio").getValue();
                        String PPtipo = (String) postSnapshot.child("tipo").getValue();
                        String modificado = String.valueOf(postSnapshot.child("modificado").getValue());
                        String productos = String.valueOf(postSnapshot.child("productos").getValue());
                        String comentario = String.valueOf(postSnapshot.child("comentario").getValue());
                        if (comentario.equals("null")) {
                            comentario = " ";
                        }
                        modificado = modificado.replace(",", "{").replace("=", "{").replace(" ", "");
                        productos = productos.replace(",", "{").replace("=", "{").replace(" ", "");
                        String[] uno;
                        int i;
                        if (!productos.equals("null")) {
                            uno = productos.split("\\{");
                            for (i = 1; i < uno.length; i++) {
                                uno[i] = uno[i].replace("}", "").replace(".", "");
                                if (!String.valueOf(postSnapshot.child("productos").child(uno[i] + "").child("nombre").getValue()).equals("null")) {
                                    accion = accion + String.valueOf(postSnapshot.child("productos").child(uno[i]).child("accion").getValue()) + ": " + String.valueOf(postSnapshot.child("productos").child(uno[i]).child("nombre").getValue()) + " \n";
                                }
                            }
                            fragmentoCompras.this.LDestinos.add(idPlatilloPedido + "°" + PPnombre + "°" + PPclasificacionMenu + "°" + PPclasificacionRecurso + "°" + PPcodigoBarras + "°" + PPestatus + "°" + PPprecio + "°" + PPtipo + "°" + accion + "°" + comentario);
                            accion = "";
                        } else if (!modificado.equals("null")) {
                            uno = modificado.split("\\{");
                            for (i = 1; i < uno.length; i += 8) {
                                accion = accion + String.valueOf(postSnapshot.child("modificado").child(uno[i]).child("accion").getValue()) + ": " + String.valueOf(postSnapshot.child("modificado").child(uno[i]).child("nombre").getValue()) + " " + String.valueOf(postSnapshot.child("modificado").child(uno[i]).child("cantidad").getValue()) + "x\n";
                            }
                            fragmentoCompras.this.LDestinos.add(idPlatilloPedido + "°" + PPnombre + "°" + PPclasificacionMenu + "°" + PPclasificacionRecurso + "°" + PPcodigoBarras + "°" + PPestatus + "°" + PPprecio + "°" + PPtipo + "°" + accion + "°" + comentario);
                            accion = "";
                        } else if (modificado.equals("null") || productos.equals("null")) {
                            fragmentoCompras.this.LDestinos.add(idPlatilloPedido + "°" + PPnombre + "°" + PPclasificacionMenu + "°" + PPclasificacionRecurso + "°" + PPcodigoBarras + "°" + PPestatus + "°" + PPprecio + "°" + PPtipo + "° °" + comentario);
                        }
                    }
                }
                fragmentoCompras.this.f_Llenar_Recycler(fragmentoCompras.this.LDestinos, v);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Cambiar_estatus_LISTO_1() {
        final DatabaseReference myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista");
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatilloPedido = postSnapshot.getKey();
                    if (!idPlatilloPedido.equals("count") && ((String) postSnapshot.child("estatus").getValue()).equals("Listo")) {
                        myRefSoporte.child(idPlatilloPedido).child("estatus").setValue("Entregado");
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Cambiar_estatus_Entregado_1() {
        final DatabaseReference myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista");
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatilloPedido = postSnapshot.getKey();
                    if (!idPlatilloPedido.equals("count")) {
                        if (String.valueOf(postSnapshot.child("productos").exists()).equals("true")) {
                            fragmentoCompras.this.f_Productos_Entregados(idPlatilloPedido);
                        }
                        String PPestatus = (String) postSnapshot.child("estatus").getValue();
                        fragmentoCompras.this.LEstatus.add(PPestatus);
                        if (PPestatus.equals("Listo")) {
                            myRefSoporte.child(idPlatilloPedido).child("estatus").setValue("Entregado");
                        }
                    }
                }
                int aux = 0;
                for (int a = 0; a < fragmentoCompras.this.LEstatus.size(); a++) {
                    if (!((String) fragmentoCompras.this.LEstatus.get(a)).equals("Entregado")) {
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
                        aux++;
                    }
                }
                if (aux == 0) {
                    FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Cambiar_estatus_enviado_1() {
        final DatabaseReference myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista");
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatilloPedido = postSnapshot.getKey();
                    if (!idPlatilloPedido.equals("count")) {
                        if (String.valueOf(postSnapshot.child("productos").exists()).equals("true")) {
                            fragmentoCompras.this.f_Productos(idPlatilloPedido);
                        }
                        if (((String) postSnapshot.child("estatus").getValue()).equals("Agregado")) {
                            myRefSoporte.child(idPlatilloPedido).child("estatus").setValue("Enviado");
                        }
                    }
                }
                fragmentoPlatillos fragmento2 = new fragmentoPlatillos();
                FragmentTransaction transicion2 = fragmentoCompras.this.getActivity().getSupportFragmentManager().beginTransaction();
                transicion2.replace(R.id.contenedor, fragmento2);
                transicion2.commit();
                Carta.btnfr3.setBackgroundResource(R.drawable.fondo_frag_des_sel);
                Carta.btnfr2.setBackgroundResource(R.drawable.fondo_frag_select);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Productos(String idPlatilloPedido) {
        final DatabaseReference myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").child(idPlatilloPedido).child("productos");
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatilloPedido = postSnapshot.getKey();
                    if (!idPlatilloPedido.equals("count")) {
                        String estatus = String.valueOf(postSnapshot.child("estatus").exists());
                        if (((String) postSnapshot.child("estatus").getValue()).equals("Agregado")) {
                            myRefSoporte.child(idPlatilloPedido).child("estatus").setValue("Enviado");
                        }
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Productos_Entregados(String idPlatilloPedido) {
        final DatabaseReference myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").child(idPlatilloPedido).child("productos");
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlatilloPedido = postSnapshot.getKey();
                    if (!idPlatilloPedido.equals("count")) {
                        String estatus = String.valueOf(postSnapshot.child("estatus").exists());
                        if (((String) postSnapshot.child("estatus").getValue()).equals("Listo")) {
                            myRefSoporte.child(idPlatilloPedido).child("estatus").setValue("Entregado");
                        }
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void f_Llenar_Recycler(ArrayList<String> msj, View v) {
        colorsCompras = new ArrayList();
        for (int i = 0; i <= msj.size() - 1; i++) {
            String[] aux = ((String) msj.get(i)).split("°");
            String id = aux[0];
            String nombre = aux[1];
            String clasificacionMenu = aux[2];
            String clasificacionRecursos = aux[3];
            String codigoBarras = aux[4];
            String estatus = aux[5];
            if (estatus.equals("Enviado") || estatus.equals("Agregado")) {
                saberSiTodosSonEntregados = "no esta listo";
            }
            colorsCompras.add(new ConstructorCompras(id, nombre, clasificacionMenu, clasificacionRecursos, codigoBarras, estatus, aux[6], aux[7], "", aux[8], aux[9]));
        }
        recyclerViewCompras = (RecyclerView) v.findViewById(R.id.recyclerViewCompras);
        recyclerViewCompras.clearOnScrollListeners();
        recyclerViewCompras.clearOnChildAttachStateChangeListeners();
        recyclerViewCompras.setAdapter(new AdaptadorCompras(colorsCompras, new ClickeadorCompras() {

            class C03132 implements DialogInterface.OnClickListener {
                C03132() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }

            public void onClick(View v, int position) {
                final String[] password = new String[1];
                String id = String.valueOf(((ConstructorCompras) fragmentoCompras.colorsCompras.get(position)).getId());
                CharSequence nom = String.valueOf(((ConstructorCompras) fragmentoCompras.colorsCompras.get(position)).getClasificacionMenu());
                if (!String.valueOf(((ConstructorCompras) fragmentoCompras.colorsCompras.get(position)).getNombre()).equals("Entregado")) {
                    final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").child(id).child("comentario");
                    Builder alertDialog = new Builder(fragmentoCompras.this.getContext());
                    alertDialog.setTitle((CharSequence) "Agregar comentario a:");
                    alertDialog.setMessage(nom);
                     final View input = new EditText(fragmentoCompras.this.getContext());
                    input.setLayoutParams(new LayoutParams(-1, -1));
                    alertDialog.setView(input);
                    alertDialog.setIcon((int) R.drawable.adoy_logo);
                    alertDialog.setPositiveButton((CharSequence) "Agregar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // password[0] = input.getText().toString();
                            Toast.makeText(fragmentoCompras.this.getContext(), password[0], Toast.LENGTH_SHORT).show();
                            myRef.setValue(password[0]);
                        }
                    });
                    alertDialog.setNegativeButton((CharSequence) "Cancelar", new C03132());
                    alertDialog.show();
                }
            }
        }));
        recyclerViewCompras.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
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
