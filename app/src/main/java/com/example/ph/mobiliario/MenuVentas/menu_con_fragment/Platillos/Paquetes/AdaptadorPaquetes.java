package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Platillos.Paquetes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPaquetes extends Adapter<AdaptadorPaquetes.PaletteViewHolder> {
    public static ArrayList<String> Lumedida = new ArrayList();
    public static ConstructorPaquetes color;
    public static List<ConstructorPaquetes> dataFrag;
    private static ClickeadorPaquetes recyclerViewOnItemClickListenerFrag;
    int colorId;
    Context context;
    SharedPreferences pref;
    int prueba;
    View row;
    int selectedPosition;

    static class PaletteViewHolder extends ViewHolder implements OnClickListener {
        private Button btnmas;
        private Button btnmenos;
        private TextView cantidad;
        private View circleView;
        private TextView clasificacionIngrediente;
        private TextView contador;
        private TextView destino;
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
            this.destino = (TextView) itemView.findViewById(R.id.destino);
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

        public TextView getDestino() {
            return this.destino;
        }

        public Button getBtnmas() {
            return this.btnmas;
        }

        public Button getBtnmenos() {
            return this.btnmenos;
        }

        public void onClick(View v) {
            AdaptadorPaquetes.recyclerViewOnItemClickListenerFrag.onClick(v, getAdapterPosition());
        }
    }

    public AdaptadorPaquetes(@NonNull List<ConstructorPaquetes> data, @NonNull ClickeadorPaquetes recyclerViewOnItemClickListener, int prueba, Context context) {
        this.dataFrag = data;
        this.recyclerViewOnItemClickListenerFrag = recyclerViewOnItemClickListener;
        this.prueba = prueba;
        this.context = context;
        pref = context.getSharedPreferences("data", 0);
    }

    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_paquetes, parent, false);
        return new PaletteViewHolder(this.row);
    }

    public void onBindViewHolder(final PaletteViewHolder holder, int position) {
        this.colorId = Color.parseColor("#4e9f30");
        color = (ConstructorPaquetes) dataFrag.get(position);
        List<ConstructorPaquetes> i = dataFrag;
        holder.getNombreIngrediente().setText(color.getNombreIngrediente());
        final int[] numero = new int[]{0};
        holder.getBtnmas().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int nivel = Integer.parseInt(holder.getNivel().getText().toString());
                int limite = Integer.parseInt(holder.getCantidad().getText().toString());
                if (AdaptadorPaquetes.this.pref.getInt(nivel + "", 0) == -1 || AdaptadorPaquetes.this.pref.getInt(nivel + "", 0) != nivel) {
                    if (numero[0] <= limite) {
                        int[] iArr = numero;
                        iArr[0] = iArr[0] + 1;
                        holder.getBtnmas().setVisibility(View.INVISIBLE);
                        holder.getBtnmenos().setVisibility(View.VISIBLE);
                    }
                    AdaptadorPaquetes.color.setContador(numero[0] + "");
                    holder.getContador().setText(AdaptadorPaquetes.color.getContador());
                    double contador = Double.parseDouble(holder.getContador().getText().toString());
                    double PU = Double.parseDouble(holder.getPrecioUnitario().getText().toString());
                    double totalPU = 0.0d;
                    if (contador > 1.0d) {
                        totalPU = (contador - 1.0d) * PU;
                    }
                    Editor editor = AdaptadorPaquetes.this.pref.edit();
                    if (contador > 0.0d) {
                        editor.putInt(nivel + "", nivel);
                        editor.commit();
                    } else {
                        editor.putInt(nivel + "", -1);
                        editor.commit();
                    }
                    if (holder.getTipo().getText().toString().equals("pt")) {
                        Carta.f_Lanzar_Cambio_Ingredientes(holder.getClasificacionIngrediente().getText().toString(), holder.getTipo().getText().toString(), holder.getNombreIngrediente().getText().toString(), holder.getPrecioUnitario().getText().toString(), holder.getIdIngre().getText().toString(), holder.getDestino().getText().toString(), "modificar", holder.getContador().getText().toString(), holder.getPrecioUnitario().getText().toString(), holder.getUMedida().getText().toString());
                    }
                    if (!holder.getTipo().getText().toString().equals("pt")) {
                        paquetes.juntar(holder.getContador().getText().toString(), holder.getNombreIngrediente().getText().toString(), holder.getIdIngre().getText().toString(), totalPU + "", holder.getUMedida().getText().toString(), holder.getClasificacionIngrediente().getText().toString(), holder.getTipo().getText().toString(), holder.getCantidad().getText().toString(), null, null, null, null, null, null, null, null);
                    }
                }
            }
        });
        holder.getBtnmenos().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int nivel = Integer.parseInt(holder.getNivel().getText().toString());
                if (AdaptadorPaquetes.this.pref.getInt(nivel + "", 0) == -1 || AdaptadorPaquetes.this.pref.getInt(nivel + "", 0) == nivel) {
                    Editor editor = AdaptadorPaquetes.this.pref.edit();
                    if (numero[0] > 0) {
                        int[] iArr = numero;
                        iArr[0] = iArr[0] - 1;
                        holder.getBtnmas().setVisibility(View.VISIBLE);
                        holder.getBtnmenos().setVisibility(View.INVISIBLE);
                    }
                    if (numero[0] > 0) {
                        editor.putInt(nivel + "", nivel);
                        editor.commit();
                    } else {
                        editor.putInt(nivel + "", -1);
                        editor.commit();
                    }
                    AdaptadorPaquetes.color.setContador(numero[0] + "");
                    holder.getContador().setText(AdaptadorPaquetes.color.getContador());
                    paquetes.juntar(holder.getContador().getText().toString(), holder.getNombreIngrediente().getText().toString(), holder.getIdIngre().getText().toString(), "", holder.getUMedida().getText().toString(), holder.getClasificacionIngrediente().getText().toString(), holder.getTipo().getText().toString(), holder.getCantidad().getText().toString(), null, null, null, null, null, null, null, null);
                }
            }
        });
        holder.getIdIngre().setText(color.getIdIngre());
        holder.getPrecioUnitario().setText(color.getPrecioUnitario());
        holder.getCantidad().setText(color.getCantidad());
        holder.getContador().setText(color.getContador());
        holder.getDestino().setText(color.getDestino());
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
