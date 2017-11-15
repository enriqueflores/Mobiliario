package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Sub;

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

import com.example.ph.mobiliario.R;

import java.util.List;

public class AdaptadorSub extends Adapter<AdaptadorSub.PaletteViewHolder> {
    public static ConstructorSub color;
    public static List<ConstructorSub> dataFrag;
    private static ClickeadorSub recyclerViewOnItemClickListenerFrag;
    int colorId;
    int selectedPosition;

    static class PaletteViewHolder extends ViewHolder implements OnClickListener {
        private Button btnmas;
        private Button btnmenos;
        private TextView cantidad;
        private View circleView;
        private TextView clasificacionIngrediente;
        private TextView contador;
        private TextView idIngre;
        private TextView nivel;
        private TextView nombreIngrediente;
        private TextView precioUnitario;
        private TextView tipo;
        private TextView uMedida;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            this.btnmas = (Button) itemView.findViewById(R.id.btnmas);
            this.btnmenos = (Button) itemView.findViewById(R.id.btnmenos);
            this.circleView = itemView.findViewById(R.id.circleView);
            this.nombreIngrediente = (TextView) itemView.findViewById(R.id.nombreIngrediente);
            this.nivel = (TextView) itemView.findViewById(R.id.nivel);
            this.precioUnitario = (TextView) itemView.findViewById(R.id.precioUnitario);
            this.cantidad = (TextView) itemView.findViewById(R.id.cantidad);
            this.contador = (TextView) itemView.findViewById(R.id.contador);
            this.clasificacionIngrediente = (TextView) itemView.findViewById(R.id.clasificacionIngrediente);
            this.tipo = (TextView) itemView.findViewById(R.id.tipo);
            this.uMedida = (TextView) itemView.findViewById(R.id.umedida);
            this.idIngre = (TextView) itemView.findViewById(R.id.idIngre);
            itemView.setOnClickListener(this);
        }

        public TextView getNombreIngrediente() {
            return this.nombreIngrediente;
        }

        public TextView getCantidad() {
            return this.cantidad;
        }

        public View getCircleView() {
            return this.circleView;
        }

        public TextView getContador() {
            return this.contador;
        }

        public TextView getClasificacionIngrediente() {
            return this.clasificacionIngrediente;
        }

        public TextView getTipo() {
            return this.tipo;
        }

        public TextView getUMedida() {
            return this.uMedida;
        }

        public TextView getIdIngre() {
            return this.idIngre;
        }

        public TextView getPrecioUnitario() {
            return this.precioUnitario;
        }

        public TextView getNivel() {
            return this.nivel;
        }

        public Button getBtnmas() {
            return this.btnmas;
        }

        public Button getBtnmenos() {
            return this.btnmenos;
        }

        public void onClick(View v) {
            AdaptadorSub.recyclerViewOnItemClickListenerFrag.onClick(v, getAdapterPosition());
        }
    }

    public AdaptadorSub(@NonNull List<ConstructorSub> data, @NonNull ClickeadorSub recyclerViewOnItemClickListener) {
        dataFrag = data;
        recyclerViewOnItemClickListenerFrag = recyclerViewOnItemClickListener;
    }

    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaletteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rowingredientes, parent, false));
    }

    public void onBindViewHolder(final PaletteViewHolder holder, int position) {
        this.colorId = Color.parseColor("#4e9f30");
        color = (ConstructorSub) dataFrag.get(position);
        List<ConstructorSub> i = dataFrag;
        holder.getNombreIngrediente().setText(color.getNombreIngrediente());
        final int[] numero = new int[]{1};
        holder.getBtnmas().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int[] iArr = numero;
                iArr[0] = iArr[0] + 1;
                AdaptadorSub.color.setContador(numero[0] + "");
                holder.getContador().setText(AdaptadorSub.color.getContador());
                double contador = Double.parseDouble(holder.getContador().getText().toString());
                double PU = Double.parseDouble(holder.getPrecioUnitario().getText().toString());
                double totalPU = 0.0d;
                if (contador > 1.0d) {
                    totalPU = (contador - 1.0d) * PU;
                }
                Ingr.juntar(holder.getContador().getText().toString(), holder.getNombreIngrediente().getText().toString(), holder.getIdIngre().getText().toString(), totalPU + "");
            }
        });
        holder.getBtnmenos().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (numero[0] > 0) {
                    int[] iArr = numero;
                    iArr[0] = iArr[0] - 1;
                }
                AdaptadorSub.color.setContador(numero[0] + "");
                holder.getContador().setText(AdaptadorSub.color.getContador());
                Ingr.juntar(holder.getContador().getText().toString(), holder.getNombreIngrediente().getText().toString(), holder.getIdIngre().getText().toString(), "");
            }
        });
        holder.getIdIngre().setText(color.getIdIngre());
        holder.getPrecioUnitario().setText(color.getPrecioUnitario());
        holder.getCantidad().setText(color.getCantidad());
        holder.getContador().setText(color.getContador());
        holder.getClasificacionIngrediente().setText(color.getClasificacionIngredientes());
        holder.getTipo().setText(color.getTipo());
        holder.getNivel().setText(color.getNivel());
        holder.getUMedida().setText(color.getUMedida());
        ((GradientDrawable) holder.getCircleView().getBackground()).setColor(this.colorId);
    }

    public int getItemCount() {
        return dataFrag.size();
    }
}

