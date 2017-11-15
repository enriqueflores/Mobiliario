package com.example.ph.mobiliario.Inicio.tikets;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph.mobiliario.MenuDinamico.Clickeador;
import com.example.ph.mobiliario.MenuDinamico.Constructor;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.R;

import java.util.List;


public class AdaptadorITikets extends RecyclerView.Adapter<AdaptadorITikets.PaletteViewHolder> {
    static public List<ConstructorITikets> data;
    private static ClickeadorITikets recyclerViewOnItemClickListener;
    int colorId;
    int selectedPosition;

    public AdaptadorITikets(@NonNull List<ConstructorITikets> data,
                            @NonNull ClickeadorITikets
                             recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inicio_tikets, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        colorId = Color.parseColor("#4e9f30");
        ConstructorITikets color = data.get(position);

        holder.getIcono().setText(color.getIcono());

        holder.getCantidad().setText(color.getCantidad());
        holder.getTitulo().setText(color.getTitulo());

        List<ConstructorITikets> i = data;

        if (holder.getTitulo().getText().toString().equals("Enviado")) {
             holder.getIcono().setTextColor(Color.parseColor("#F44336"));
        }
        if (holder.getTitulo().getText().toString().equals("Espera")) {
            holder.getIcono().setTextColor(Color.parseColor("#FFEB3B"));
        }
        if (holder.getTitulo().getText().toString().equals("Resolviendo")) {
            holder.getIcono().setTextColor(Color.parseColor("#009688"));
        }
        if (holder.getTitulo().getText().toString().equals("Resuelto")) {
            holder.getIcono().setTextColor(Color.parseColor("#4CAF50"));
        }









        GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();

        gradientDrawable.setColor(colorId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class PaletteViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private View circleView;
        private TextView titulo;
        private TextView cantidad;
        private TextView icono;


        public PaletteViewHolder(View itemView) {
            super(itemView);

            circleView = itemView.findViewById(R.id.circleView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            cantidad = (TextView) itemView.findViewById(R.id.cantidad);
            icono = (TextView) itemView.findViewById(R.id.icono);

            //titleTextView.setTypeface(Historial.font);
            icono.setTypeface(MenuDinamico.font);

            itemView.setOnClickListener(this);
        }


        public TextView getTitulo() {
            return titulo;
        }

        public TextView getCantidad() {
            return cantidad;
        }

        public View getCircleView() {
            return circleView;
        }

        public TextView getIcono() {
            return icono;
        }


        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }


}

