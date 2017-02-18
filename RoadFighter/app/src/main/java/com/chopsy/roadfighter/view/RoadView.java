package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.chopsy.roadfighter.controller.RoadController;

public class RoadView extends View {
    private Paint mPaint;
    private DashPathEffect mDashPathEffect;
    private Path mPath;
    private int mWidth;
    boolean blackFirst = true;


    public RoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        new RoadController(this);
        mPaint = new Paint();
        mDashPathEffect = new DashPathEffect(new float[]{50, 50}, 0);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(getWidth() / 3 + getWidth() / 6, 0);
        mPath.lineTo(getWidth() / 3 + getWidth() / 6, getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mWidth = w;
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(mWidth / 3 + mWidth / 6, 0);
        mPath.lineTo(mWidth / 3 + mWidth / 6, getHeight());
    }


    @Override
    synchronized public void onDraw(Canvas canvas) {

        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(), mPaint);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(), mPaint);

        mPaint.setPathEffect(mDashPathEffect);
        canvas.drawPath(mPath, mPaint);
    }

    protected void reDraw() {
        this.invalidate();
    }

    public void updateRoadView() {
        mPath.reset();

        if (blackFirst) {
            mPath.moveTo(mWidth / 3 + mWidth / 6, 0);
            mPath.lineTo(mWidth / 3 + mWidth / 6, getHeight());
        } else {
            mPath.moveTo(mWidth / 3 + mWidth / 6, 50);
            mPath.lineTo(mWidth / 3 + mWidth / 6, 50 + getHeight());
        }
        blackFirst = !blackFirst;
        reDraw();
    }
}