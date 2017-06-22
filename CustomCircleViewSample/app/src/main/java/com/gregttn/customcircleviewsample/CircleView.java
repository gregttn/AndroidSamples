package com.gregttn.customcircleviewsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private boolean showInnerCircle = false;

    private Paint circleFill;
    private RectF circleFrame;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);

        try {
            showInnerCircle = attributes.getBoolean(R.styleable.CircleView_showInnerCircle, false);
        } finally {
            attributes.recycle();
        }

        init();
    }

    private void init() {
        circleFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleFill.setStyle(Paint.Style.FILL);
        circleFill.setColor(0xff303F9F);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        circleFrame = new RectF(0, 0, getWidth(), getHeight());

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(circleFrame, circleFill);
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
