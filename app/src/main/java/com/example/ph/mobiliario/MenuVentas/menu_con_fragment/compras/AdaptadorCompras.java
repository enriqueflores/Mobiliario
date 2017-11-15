package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.compras;

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
import com.example.ph.mobiliario.Mesas.Mesa;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdaptadorCompras extends Adapter<AdaptadorCompras.PaletteViewHolder> {
    public static List<ConstructorCompras> data;
    private static ClickeadorCompras recyclerViewOnItemClickListener;
    int colorId;
    View row;

    static class PaletteViewHolder extends ViewHolder implements OnClickListener {
        private TextView accion;
        private Button btneliminar;
        private View circleView;
        private TextView clasificacionMenu;
        private TextView clasificacionRecurso;
        private TextView codigoBarras;
        private TextView comentario;
        private TextView destino;
        private TextView estatus;
        private TextView id;
        private TextView nombre;
        private TextView precio;
        private TextView tipo;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            this.btneliminar = (Button) itemView.findViewById(R.id.btneliminar);
            this.circleView = itemView.findViewById(R.id.circleView);
            this.id = (TextView) itemView.findViewById(R.id.id);
            this.clasificacionMenu = (TextView) itemView.findViewById(R.id.clasificacionMenu);
            this.clasificacionRecurso = (TextView) itemView.findViewById(R.id.clasificacionRecurso);
            this.codigoBarras = (TextView) itemView.findViewById(R.id.codigoBarras);
            this.destino = (TextView) itemView.findViewById(R.id.destino);
            this.comentario = (TextView) itemView.findViewById(R.id.comentario);
            this.estatus = (TextView) itemView.findViewById(R.id.estatus);
            this.accion = (TextView) itemView.findViewById(R.id.accion);
            this.nombre = (TextView) itemView.findViewById(R.id.nombre);
            this.precio = (TextView) itemView.findViewById(R.id.precio);
            this.tipo = (TextView) itemView.findViewById(R.id.tipo);
            this.id.setTypeface(MenuDinamico.font);
            itemView.setOnClickListener(this);
        }

        public TextView getId() {
            return this.id;
        }

        public TextView getClasificacionMenu() {
            return this.clasificacionMenu;
        }

        public View getCircleView() {
            return this.circleView;
        }

        public TextView getClasificacionRecurso() {
            return this.clasificacionRecurso;
        }

        public TextView getComentario() {
            return this.comentario;
        }

        public TextView getCodigoBarras() {
            return this.codigoBarras;
        }

        public TextView getEstatus() {
            return this.estatus;
        }

        public TextView getNombre() {
            return this.nombre;
        }

        public TextView getPrecio() {
            return this.precio;
        }

        public TextView getTipo() {
            return this.tipo;
        }

        public TextView getDestino() {
            return this.destino;
        }

        public TextView getAccion() {
            return this.accion;
        }

        public Button getBtneliminar() {
            return this.btneliminar;
        }

        public void onClick(View v) {
            AdaptadorCompras.recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public AdaptadorCompras(@NonNull List<ConstructorCompras> data, @NonNull ClickeadorCompras recyclerViewOnItemClickListener) {
        data = data;
        recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowcartacompras, parent, false);
        return new PaletteViewHolder(this.row);
    }

    public void onBindViewHolder(final PaletteViewHolder holder, int position) {
        this.colorId = Color.parseColor("#4e9f30");
        ConstructorCompras color = (ConstructorCompras) data.get(position);
        //new int[1][0] = 0;
        holder.getBtneliminar().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(Mesa.idComanda).child("Lista").child(holder.getDestino().getText().toString()).child(holder.getId().getText().toString()).setValue(null);
                fragmentoCompras.f_Saber_Precio1();
                AdaptadorCompras.this.f_saber_existencia(holder.getClasificacionRecurso().getText().toString(), holder.getEstatus().getText().toString());
            }
        });
        holder.getId().setText(color.getId());
        holder.getDestino().setText(color.getDestino());
        holder.getClasificacionMenu().setText(color.getClasificacionMenu());
        holder.getClasificacionRecurso().setText(color.getClasificacionRecurso());
        List<ConstructorCompras> i = data;
        holder.getCodigoBarras().setText(color.getCodigoBarras());
        holder.getEstatus().setText(color.getEstatus());
        holder.getComentario().setText(color.getComentario());
        holder.getNombre().setText(color.getNombre());
        if (holder.getNombre().getText().toString().equals("Entregado")) {
            holder.btneliminar.setVisibility(4);
        }
        if (holder.getNombre().getText().toString().equals("Enviado")) {
            this.row.setBackgroundResource(R.drawable.mensajes_enviados);
        } else if (holder.getNombre().getText().toString().equals("Agregado")) {
            this.row.setBackgroundResource(R.drawable.fondo_menu_categorias);
        } else if (holder.getNombre().getText().toString().equals("Entregado")) {
            this.row.setBackgroundResource(R.drawable.fondo_estatus_paltillo_enviado);
        }
        holder.getPrecio().setText(color.getPrecio());
        holder.getTipo().setText(color.getTipo());
        holder.getAccion().setText(color.getAccion());
        ((GradientDrawable) holder.getCircleView().getBackground()).setColor(this.colorId);
    }

    private void f_saber_existencia(final String idCategoria, final String idPlatillo) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("menu").child(idCategoria).child(idPlatillo).child("existencia").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdaptadorCompras.this.f_des_descuentos(String.valueOf(dataSnapshot.getValue()), "1", idPlatillo, idCategoria);
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void f_des_descuentos(String almacen, String cantidad, String idPlatillo, String idCategoria) {
        int resta = Integer.parseInt(almacen) + Integer.parseInt(cantidad);
        DatabaseReference myRefDescuentos = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("menu").child(idCategoria).child(idPlatillo).child("existencia");
        if (!idCategoria.equals("null")) {
            myRefDescuentos.setValue(resta + "");
        }
    }

    public int getItemCount() {
        return data.size();
    }
}
