package com.gregttn.customcircleviewsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class CircleView extends View {
    private final GestureDetector gestureDetector;

    private boolean showInnerCircle = false;
    private Paint circleFill;
    private Paint innerCircleFill;
    private RectF circleFrame;
    private RectF innerCircleFrame;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);

        try {
            showInnerCircle = attributes.getBoolean(R.styleable.CircleView_showInnerCircle, false);
        } finally {
            attributes.recycle();
        }

        init();

        gestureDetector = new GestureDetector(context, new CustomGestureListener());
    }

    private void init() {
        circleFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleFill.setStyle(Paint.Style.FILL);
        circleFill.setColor(0xff303F9F);

        innerCircleFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCircleFill.setStyle(Paint.Style.FILL);
        innerCircleFill.setColor(0xffffff00);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        circleFrame = new RectF(getPaddingLeft(),
                getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom()
            );

        int innerXInset = w / 4;
        int innerYInset = h / 4;

        innerCircleFrame = new RectF(circleFrame);
        innerCircleFrame.inset(innerXInset, innerYInset);

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(circleFrame, circleFill);

        if(showInnerCircle) {
            canvas.drawOval(innerCircleFrame, innerCircleFill);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public boolean isShowInnerCircle() {
        return showInnerCircle;
    }

    public void setShowInnerCircle(boolean showInnerCircle) {
        this.showInnerCircle = showInnerCircle;
        invalidate();
        requestLayout();
    }

    class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(getClass().getName(), "onDown event");

            return true;
        }
    }
}
