package com.example.ph.mobiliario.Actividad;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph.mobiliario.Chat.chat2;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.R;

import java.util.List;


public class AdaptadorActividad extends RecyclerView.Adapter<AdaptadorActividad.PaletteViewHolder> {
    static public List<ConstructorActividad> data;
    private static ClickeadorActividad recyclerViewOnItemClickListener;
    int colorId;
    int selectedPosition;

    public AdaptadorActividad(@NonNull List<ConstructorActividad> data,
                              @NonNull ClickeadorActividad
                             recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowactividad, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        colorId = android.graphics.Color.parseColor("#4e9f34");
        ConstructorActividad color = data.get(position);

        holder.getIcono().setText(color.getIcono());


        List<ConstructorActividad> i = data;
        //  holder.getIcono().setTextColor(Color.GREEN);
        //PALOMITA
        if (color.getTabla().equals("no-leido")) {
           colorId = android.graphics.Color.parseColor("#f44336");
        }
        //TACHE
        if (color.getTabla().equals("leido")) {
            colorId = android.graphics.Color.parseColor("#009688");
        }

        //TACHE
        if (color.getIcono().equals("\uF00D")) {
            colorId = android.graphics.Color.parseColor("#f44336");
        }
        //CALENDARIO
        if (color.getIcono().equals("\uF073")) {
            colorId = android.graphics.Color.parseColor("#2196f3");
        }

        //LAPICITO
        if (color.getIcono().equals("\uF040")) {
            colorId = android.graphics.Color.parseColor("#ef6c00");
        }
        //LAPICITO
        if (color.getIcono().equals("\uF088")) {
            colorId = android.graphics.Color.parseColor("#e040fb");
        }
        //LAPICITO
        if (color.getIcono().equals("\uF046")) {
            colorId = android.graphics.Color.parseColor("#00897b");
        }

        holder.getTitleTextView().setText(color.getName());
        holder.getTabla().setText(color.getTabla());
        GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();

        if (color.getIcono().equals(Login.uidUsuario))
        {
            holder.getTitleTextView().setGravity(Gravity.END);
            holder.getTitleTextView().setTextColor(holder.getTitleTextView().getContext().getResources().getColor(R.color.text_grey));

            //provisional
            holder.getCircleView().setVisibility(View.VISIBLE);
        }
        else if (color.getIcono().equals(chat2.keySeleccionado))
        {

            holder.getTitleTextView().setGravity(Gravity.TOP);

            holder.getCircleView().setVisibility(View.INVISIBLE);
        }


       // holder.getTitleTextView().setTextColor(holder.getTitleTextView().getContext().getResources().getColor(R.color.colorPrimaryDark));



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

