package com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Categorias;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ItemDecoration;

public class DividerCategorias extends ItemDecoration {
    private static final int[] ATTRS = new int[]{16843284};

    public DividerCategorias(Context context) {
        context.obtainStyledAttributes(ATTRS).recycle();
    }
}