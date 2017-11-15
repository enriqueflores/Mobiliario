package com.example.ph.mobiliario.Chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;


public class DividerChat extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    public DividerChat(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        styledAttributes.recycle();
    }
}