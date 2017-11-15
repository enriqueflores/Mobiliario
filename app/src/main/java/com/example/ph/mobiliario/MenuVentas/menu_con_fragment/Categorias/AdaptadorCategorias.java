package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias;

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

import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.R;

import java.util.List;

public class AdaptadorCategorias extends Adapter<AdaptadorCategorias.PaletteViewHolder> {
    public static List<ConstructorCategorias> data;
    private static ClickeadorCategorias recyclerViewOnItemClickListener;
    int colorId;
    View row;
    int selectedPosition;

    static class PaletteViewHolder extends ViewHolder implements OnClickListener {
        private View circleView;
        private TextView icono;
        private TextView tabla;
        private TextView titleTextView;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            this.circleView = itemView.findViewById(R.id.circleView);
            this.titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            this.tabla = (TextView) itemView.findViewById(R.id.tabla);
            this.icono = (TextView) itemView.findViewById(R.id.icono);
            this.icono.setTypeface(MenuDinamico.font);
            itemView.setOnClickListener(this);
        }

        public TextView getTitleTextView() {
            return this.titleTextView;
        }

        public TextView getTabla() {
            return this.tabla;
        }

        public View getCircleView() {
            return this.circleView;
        }

        public TextView getIcono() {
            return this.icono;
        }

        public void onClick(View v) {
            AdaptadorCategorias.recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public AdaptadorCategorias(@NonNull List<ConstructorCategorias> data, @NonNull ClickeadorCategorias recyclerViewOnItemClickListener) {
        data = data;
        recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowcartacategorias, parent, false);
        return new PaletteViewHolder(this.row);
    }

    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        this.colorId = Color.parseColor("#4e9f30");
        ConstructorCategorias color = (ConstructorCategorias) data.get(position);
        holder.getIcono().setText(color.getIcono());
        List<ConstructorCategorias> i = data;
        if (color.getTabla().equals("ocupada")) {
            this.row.setBackgroundColor(Color.parseColor("#B71C1C"));
        } else if (color.getTabla().equals("libre")) {
            this.row.setBackgroundColor(Color.parseColor("#1B5E20"));
        }
        holder.getTitleTextView().setText(color.getName());
        holder.getTabla().setText(color.getTabla());
        ((GradientDrawable) holder.getCircleView().getBackground()).setColor(this.colorId);
    }

    public int getItemCount() {
        return data.size();
    }
}
