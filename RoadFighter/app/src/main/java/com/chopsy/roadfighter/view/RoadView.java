package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chopsy.roadfighter.R;

public class RoadView extends View implements View.OnTouchListener {
    private Paint mPaint;
    private DashPathEffect mDashPathEffect;
    private Path mPath;
    private int mWidth;
    private int mHeight;
    boolean blackFirst = true;
    private Handler mHandler;


    public RoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.GREEN);
        mPaint = new Paint();
        mDashPathEffect = new DashPathEffect(new float[]{50, 50}, 0);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(getWidth() / 3 + getWidth() / 6, 0);
        mPath.lineTo(getWidth() / 3 + getWidth() / 6, getHeight());
        setOnTouchListener(this);
//        mPath.close();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mWidth = w;
        mHeight = h;
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(mWidth / 3 + mWidth / 6, 0);
        mPath.lineTo(mWidth / 3 + mWidth / 6, getHeight());
    }


    @Override
    synchronized public void onDraw(Canvas canvas) {

        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.image_road);
        canvas.drawBitmap(b1, 0, 0, mPaint);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mHandler != null) return true;
                mHandler = new Handler();
                mHandler.postDelayed(mAction, 500);
                break;
            case MotionEvent.ACTION_UP:
                if (mHandler == null) return true;
                mHandler.removeCallbacks(mAction);
                mHandler = null;
                break;
        }
        return true;
    }

    Runnable mAction = new Runnable() {
        @Override
        public void run() {
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
            mHandler.postDelayed(this, 500);
        }
    };
}