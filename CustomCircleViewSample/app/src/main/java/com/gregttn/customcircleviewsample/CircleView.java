package com.gregttn.customcircleviewsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private boolean showInnerCircle = false;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);

        try {
            showInnerCircle = attributes.getBoolean(R.styleable.CircleView_showInnerCircle, false);
        } finally {
            attributes.recycle();
        }
    }


    public boolean isShowInnerCircle() {
        return showInnerCircle;
    }

    public void setShowInnerCircle(boolean showInnerCircle) {
        this.showInnerCircle = showInnerCircle;
        invalidate();
        requestLayout();
    }
}
