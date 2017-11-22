package com.example.ph.mobiliario.Inicio.Comandas;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.R;

import java.util.List;

public class AdaptadorIComandas extends Adapter<AdaptadorIComandas.PaletteViewHolder> {
    public static List<ConstructorIComandas> data;
    private static ClickeadorIComandas recyclerViewOnItemClickListener;
    int colorId;
    int selectedPosition;

    static class PaletteViewHolder extends ViewHolder implements OnClickListener {
        private TextView cantidad;
        private View circleView;
        private TextView icono;
        private TextView titulo;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            this.circleView = itemView.findViewById(R.id.circleView);
            this.titulo = (TextView) itemView.findViewById(R.id.titulo);
            this.cantidad = (TextView) itemView.findViewById(R.id.cantidad);
            this.icono = (TextView) itemView.findViewById(R.id.icono);
            this.icono.setTypeface(Login.font);
            itemView.setOnClickListener(this);
        }

        public TextView getTitulo() {
            return this.titulo;
        }

        public TextView getCantidad() {
            return this.cantidad;
        }

        public View getCircleView() {
            return this.circleView;
        }

        public TextView getIcono() {
            return this.icono;
        }

        public void onClick(View v) {
            AdaptadorIComandas.recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public AdaptadorIComandas(@NonNull List<ConstructorIComandas> data, @NonNull ClickeadorIComandas recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaletteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inicio_comandas, parent, false));
    }

    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        this.colorId = Color.parseColor("#4e9f30");
        ConstructorIComandas color = (ConstructorIComandas) data.get(position);
        holder.getIcono().setText(color.getIcono());
        holder.getCantidad().setText(color.getCantidad());
        holder.getTitulo().setText(color.getTitulo());
        List<ConstructorIComandas> i = data;
        if (holder.getTitulo().getText().toString().equals("Entregadas")) {
            holder.getIcono().setTextColor(Color.parseColor("#4CAF50"));
        }
        if (holder.getTitulo().getText().toString().equals("Cocinando")) {
            holder.getIcono().setTextColor(Color.parseColor("#FF5722"));
        }
        if (holder.getTitulo().getText().toString().equals("Pagando")) {
            holder.getIcono().setTextColor(Color.parseColor("#009688"));
        }
        ((GradientDrawable) holder.getCircleView().getBackground()).setColor(this.colorId);
    }

    public int getItemCount() {
        return data.size();
    }
}
