package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.chopsy.roadfighter.controller.RoadController;

public class RoadView extends AbstractDrawableView {

    private DashPathEffect mDashPathEffect;
    private Path mPath;
    private boolean blackStripFirst = true;

    public RoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        new RoadController(this);
        mDashPathEffect = new DashPathEffect(new float[]{50, 50}, 0);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(getWidth() / 3 + getWidth() / 6, 0);
        mPath.lineTo(getWidth() / 3 + getWidth() / 6, getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(mWidth / 3 + mWidth / 6, 0);
        mPath.lineTo(mWidth / 3 + mWidth / 6, getHeight());
    }


    @Override
    synchronized public void onDraw(Canvas canvas) {

        mPaint.setStrokeWidth(10);

        mPaint.setColor(Color.parseColor("#757575"));
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(), mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(mDashPathEffect);
        canvas.drawPath(mPath, mPaint);
    }

    public void updateRoadView() {
        mPath.reset();

        if (blackStripFirst) {
            mPath.moveTo(mWidth / 3 + mWidth / 6, 0);
            mPath.lineTo(mWidth / 3 + mWidth / 6, getHeight());
        } else {
            mPath.moveTo(mWidth / 3 + mWidth / 6, 50);
            mPath.lineTo(mWidth / 3 + mWidth / 6, 50 + getHeight());
        }
        blackStripFirst = !blackStripFirst;
        reDraw();
    }
}