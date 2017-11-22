package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias.fragmentoCategorias;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras.fragmentoCompras;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdaptadorPlatillos extends Adapter<AdaptadorPlatillos.PaletteViewHolder> {
    public static List<ConstructorPlatillos> data;
    private static ClickeadorPlatillos recyclerViewOnItemClickListener;
    public static int value;
    int colorId;
    View row;
    int selectedPosition;

    static class C07314 implements ValueEventListener {
        C07314() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            String valor = String.valueOf(dataSnapshot.getValue());
            String valorExiste = String.valueOf(dataSnapshot.exists());
            if (valorExiste.equals("true")) {
                AdaptadorPlatillos.value = Integer.parseInt(valor);
            } else if (valorExiste.equals("false")) {
                AdaptadorPlatillos.value = 0;
            }
        }

        public void onCancelled(DatabaseError error) {
        }
    }

    static class PaletteViewHolder extends ViewHolder implements OnClickListener {
        private Button btnmas;
        private Button btnmenos;
        private Button btnsend;
        private View circleView;
        private TextView clasificcion1;
        private TextView contador;
        private TextView descripcion;
        private TextView destino;
        private TextView existencia;
        private TextView idPlatillo;
        private TextView nombre;
        private TextView precio;
        private TextView tipo;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            AdaptadorPlatillos.f_count();
            this.btnmas = (Button) itemView.findViewById(R.id.btnmas);
            this.btnmenos = (Button) itemView.findViewById(R.id.btnmenos);
            this.btnsend = (Button) itemView.findViewById(R.id.btnsend);
            this.circleView = itemView.findViewById(R.id.circleView);
            this.nombre = (TextView) itemView.findViewById(R.id.nombre);
            this.tipo = (TextView) itemView.findViewById(R.id.tipo);
            this.destino = (TextView) itemView.findViewById(R.id.destino);
            this.existencia = (TextView) itemView.findViewById(R.id.existencia);
            this.descripcion = (TextView) itemView.findViewById(R.id.descricion);
            this.precio = (TextView) itemView.findViewById(R.id.precio);
            this.clasificcion1 = (TextView) itemView.findViewById(R.id.clasificacion);
            this.contador = (TextView) itemView.findViewById(R.id.contador);
            this.idPlatillo = (TextView) itemView.findViewById(R.id.idPlatillo);
            this.clasificcion1.setTypeface(MenuDinamico.font);
            itemView.setOnClickListener(this);
        }

        public TextView getNombre() {
            return this.nombre;
        }

        public TextView getExistencia() {
            return this.existencia;
        }

        public TextView getDestino() {
            return this.destino;
        }

        public View getCircleView() {
            return this.circleView;
        }

        public TextView getClasificcion1() {
            return this.clasificcion1;
        }

        public TextView getDescripcion() {
            return this.descripcion;
        }

        public TextView getContador() {
            return this.contador;
        }

        public TextView getPrecio() {
            return this.precio;
        }

        public TextView getTipo() {
            return this.tipo;
        }

        public TextView getIdPlatillo() {
            return this.idPlatillo;
        }

        public Button getBtnmas() {
            return this.btnmas;
        }

        public Button getBtnmenos() {
            return this.btnmenos;
        }

        public Button getBtnsend() {
            return this.btnsend;
        }

        public void onClick(View v) {
            AdaptadorPlatillos.recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public AdaptadorPlatillos(@NonNull List<ConstructorPlatillos> data, @NonNull ClickeadorPlatillos recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowcartaplatillos, parent, false);
        return new PaletteViewHolder(this.row);
    }

    public void onBindViewHolder(final PaletteViewHolder holder, int position) {
        this.colorId = Color.parseColor("#4e9f30");
        ConstructorPlatillos color = (ConstructorPlatillos) data.get(position);
        final int[] numero = new int[]{0};
        holder.getBtnmas().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int d = Integer.parseInt(holder.getDescripcion().getText().toString());
                int[] iArr;
                if (!holder.getTipo().getText().toString().equals("mp")) {
                    iArr = numero;
                    iArr[0] = iArr[0] + 1;
                } else if (d > numero[0]) {
                    iArr = numero;
                    iArr[0] = iArr[0] + 1;
                }
                holder.getContador().setText(numero[0] + "");
                if (numero[0] > 0) {
                    holder.getBtnsend().setVisibility(View.VISIBLE);
                } else if (numero[0] == 0) {
                    holder.getBtnsend().setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.getBtnmenos().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (numero[0] > 0) {
                    int[] iArr = numero;
                    iArr[0] = iArr[0] - 1;
                }
                if (numero[0] > 0) {
                    holder.getBtnsend().setVisibility(View.VISIBLE);
                } else if (numero[0] == 0) {
                    holder.getBtnsend().setVisibility(View.INVISIBLE);
                }
                holder.getContador().setText(numero[0] + "");
            }
        });
        holder.getBtnsend().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdaptadorPlatillos.f_enviarPedido(numero[0], holder.getNombre().getText().toString(), holder.getPrecio().getText().toString(), holder.getExistencia().getText().toString(), holder.getTipo().getText().toString(), holder.getIdPlatillo().getText().toString(), holder.getDestino().getText().toString());
                int a = Integer.parseInt(holder.getDescripcion().getText().toString());
                if (!holder.getTipo().getText().toString().equals("pt")) {
                    AdaptadorPlatillos.this.f_descuentos(holder.getDescripcion().getText().toString(), holder.getContador().getText().toString(), holder.getIdPlatillo().getText().toString());
                } else if (holder.getTipo().getText().toString().equals("pt") && a >= 1) {
                    AdaptadorPlatillos.this.f_descuentos(holder.getDescripcion().getText().toString(), holder.getContador().getText().toString(), holder.getIdPlatillo().getText().toString());
                }
                numero[0] = 0;
                if (numero[0] > 0) {
                    holder.getBtnsend().setVisibility(View.VISIBLE);
                } else if (numero[0] == 0) {
                    holder.getBtnsend().setVisibility(View.INVISIBLE);
                }
                holder.getContador().setText(numero[0] + "");
                fragmentoCompras.f_Saber_Precio1();
            }
        });
        holder.getClasificcion1().setText(color.getClasificacion1());
        holder.getPrecio().setText(color.getPrecio());
        holder.getDescripcion().setText(color.getDescripcion());
        List<ConstructorPlatillos> i = data;
        if (color.getExixtencia().equals("ocupada")) {
            this.row.setBackgroundColor(Color.parseColor("#B71C1C"));
        } else if (color.getExixtencia().equals("libre")) {
            this.row.setBackgroundColor(Color.parseColor("#1B5E20"));
        }
        holder.getNombre().setText(color.getNombre());
        holder.getIdPlatillo().setText(color.getIdPlatillo());
        holder.getExistencia().setText(color.getExixtencia());
        holder.getDestino().setText(color.getDestino());
        if (holder.getDestino().getText().toString().equals("null")) {
            holder.getBtnmas().setVisibility(View.INVISIBLE);
            holder.getBtnmenos().setVisibility(View.INVISIBLE);
            holder.getContador().setVisibility(View.INVISIBLE);
        }
        holder.getTipo().setText(color.getTipo());
        ((GradientDrawable) holder.getCircleView().getBackground()).setColor(this.colorId);
    }

    private void f_descuentos(String almacen, String cantidad, String idPlatillo) {
        int resta = Integer.parseInt(almacen) - Integer.parseInt(cantidad);
        if (resta < 0) {
            resta = 0;
        }
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("menu").child(fragmentoCategorias.selCategoria).child(idPlatillo).child("existencia").setValue(resta + "");
    }

    public static void f_count() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").child("count").addValueEventListener(new C07314());
    }

    public static void f_enviarPedido(int numero, String nombre, String precio, String clasificacion1, String tipo, String idPlatillo, String destino) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda);
        myRef.child("idMesero").setValue(Login.uidUsuario);
        myRef.child("nombreMesero").setValue(Login.name);
        myRef.child("mesa").setValue(Mesa.idMesaSel);
        myRef.child("nombre").setValue(Mesa.nomMesa);
        value++;
        for (int i = 1; i <= numero; i++) {
            myRef.child("Lista").child(value + "").child("clasificacionMenu").setValue(fragmentoCategorias.selCategoria);
            myRef.child("Lista").child(value + "").child("nombre").setValue(nombre);
            myRef.child("Lista").child(value + "").child("codigoBarras").setValue(idPlatillo);
            myRef.child("Lista").child(value + "").child("estatus").setValue("Agregado");
            myRef.child("Lista").child(value + "").child("precio").setValue(precio);
            myRef.child("Lista").child(value + "").child("clasificacionRecurso").setValue(clasificacion1);
            myRef.child("Lista").child(value + "").child("tipo").setValue(tipo);
            myRef.child("Lista").child(value + "").child("destino").setValue(destino);
            myRef.child("Lista").child("count").setValue(value + "");
            value++;
        }
    }

    public int getItemCount() {
        return data.size();
    }
}


