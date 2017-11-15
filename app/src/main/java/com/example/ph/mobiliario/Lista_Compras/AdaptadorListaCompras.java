package com.example.ph.mobiliario.Lista_Compras;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph.mobiliario.Actividad.ClickeadorActividad;
import com.example.ph.mobiliario.Actividad.ConstructorActividad;
import com.example.ph.mobiliario.Chat.chat2;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.R;

import java.util.List;


public class AdaptadorListaCompras extends RecyclerView.Adapter<AdaptadorListaCompras.PaletteViewHolder> {
    static public List<ConstructorListaCompras> data;
    private static ClickeadorListaCompras recyclerViewOnItemClickListener;
    int colorId;
    int selectedPosition;

    public AdaptadorListaCompras(@NonNull List<ConstructorListaCompras> data,
                                 @NonNull ClickeadorListaCompras
                             recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlistacompras, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        colorId = android.graphics.Color.parseColor("#4e9f34");
        ConstructorListaCompras color = data.get(position);

        List<ConstructorListaCompras> i = data;
        //  holder.getIcono().setTextColor(Color.GREEN);
        //PALOMITA
       /* if (color.getTabla().equals("no-leido")) {
           colorId = android.graphics.Color.parseColor("#f44336");
        }*/

        holder.getCmax().setText(color.getCmax());
        holder.getCmin().setText(color.getCmin());
        holder.getDescripcion().setText(color.getDescripcion());
        holder.getExistencia().setText(color.getExistencia());
        holder.getNombre().setText(color.getNombre());
        holder.getTipo().setText(color.getTipo());
        holder.getUmedida().setText(color.getUmedida());

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
        private TextView cmax;
        private TextView cmin;
        private TextView descripcion;
        private TextView existencia;
        private TextView nombre;
        private TextView tipo;
        private TextView umedida;


        public PaletteViewHolder(View itemView) {
            super(itemView);

            circleView = itemView.findViewById(R.id.circleView);
            cmax = (TextView) itemView.findViewById(R.id.cmax);
            cmin = (TextView) itemView.findViewById(R.id.cmin);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            existencia = (TextView) itemView.findViewById(R.id.existencia);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            tipo = (TextView) itemView.findViewById(R.id.tipo);
            umedida = (TextView) itemView.findViewById(R.id.umedida);

            //titleTextView.setTypeface(Historial.font);
           // icono.setTypeface(MenuDinamico.font);


            itemView.setOnClickListener(this);
        }

        public View getCircleView() {
            return circleView;
        }

        public TextView getCmax() {
            return cmax;
        }

        public TextView getCmin() {
            return cmin;
        }

        public TextView getDescripcion() {
            return descripcion;
        }

        public TextView getExistencia() {
            return existencia;
        }

        public TextView getNombre() {
            return nombre;
        }

        public TextView getTipo() {
            return tipo;
        }

        public TextView getUmedida() {
            return umedida;
        }


        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }


}

