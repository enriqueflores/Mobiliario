package com.example.ph.mobiliario.MenuDinamico.SubMenuDinamico;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;


public class DividerFrag extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    public DividerFrag(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        styledAttributes.recycle();
    }
}