package com.example.ph.mobiliario.Mesas;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.R;

import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;


public class AdaptadorMesa extends RecyclerView.Adapter<AdaptadorMesa.PaletteViewHolder> {
    static public List<ConstructorMesa> data;
    private static ClickeadorMesa recyclerViewOnItemClickListener;
    int colorId;
    int selectedPosition;
    View row;

    public AdaptadorMesa(@NonNull List<ConstructorMesa> data,
                         @NonNull ClickeadorMesa
                                 recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowmesa, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        colorId = Color.parseColor("#4e9f30");
        ConstructorMesa color = data.get(position);

        holder.getIcono().setText(color.getIcono());
        //holder.getIcono().setTextColor(R.color.colorAccent);


        List<ConstructorMesa> i = data;
        //  holder.getIcono().setTextColor(Color.GREEN);
        //PALOMITA
      /*  if (color.getIcono().equals("\uF00C")) {
            colorId = android.graphics.Color.parseColor("#009688");
        }*/
        if (color.getTabla().equals("Ocupada")) {
            //row.setBackgroundColor(Color.parseColor("#B71C1C"));
            row.setBackgroundResource(R.drawable.fondo_mesa_ocupada);
        } else if (color.getTabla().equals("Libre")) {
            row.setBackgroundResource(R.drawable.fondo_mesa_libre);
        } else if (color.getTabla().equals("Reservada")) {
            row.setBackgroundResource(R.drawable.fondo_mesa_reservada);
        }


        holder.getTitleTextView().setText(color.getName());


        holder.getTabla().setText(color.getTabla());
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
        private TextView titleTextView;
        private TextView tabla;
        private TextView icono;


        public PaletteViewHolder(View itemView) {
            super(itemView);

            circleView = itemView.findViewById(R.id.circleView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            tabla = (TextView) itemView.findViewById(R.id.tabla);
            icono = (TextView) itemView.findViewById(R.id.icono);

            //titleTextView.setTypeface(Historial.font);
            icono.setTypeface(MenuDinamico.font);

            itemView.setOnClickListener(this);
        }


        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getTabla() {
            return tabla;
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

