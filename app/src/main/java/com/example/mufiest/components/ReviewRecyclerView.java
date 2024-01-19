package com.example.mufiest.components;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

public class ReviewRecyclerView extends RecyclerView {

    private int maxHeight = 500;

    public ReviewRecyclerView(Context context) {
        super(context);
    }

    public ReviewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReviewRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}