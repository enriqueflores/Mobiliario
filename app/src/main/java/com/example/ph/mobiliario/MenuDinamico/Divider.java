package com.example.ph.mobiliario.MenuDinamico;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;


public class Divider extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    public Divider(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        styledAttributes.recycle();
    }
}