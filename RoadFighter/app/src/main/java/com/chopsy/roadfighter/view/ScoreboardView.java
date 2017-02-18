package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;

public class ScoreboardView extends View {

    private int mTime;
    private int mDistanceCovered;
    private int mCurrentSpeed;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Handler mHandler;

    public ScoreboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mHandler = new Handler();
        mHandler.postDelayed(mAction, 1000);
//        Timer timer = new Timer();
//        UpdateTimeTask updateTimerTask = new UpdateTimeTask(this);
//        timer.schedule(updateTimerTask, 1000, 1000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mWidth = w;
        mHeight = h;
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
//        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        canvas.drawRect(2 * mWidth / 3 + 10, mHeight / 6 - 10, mWidth - 10, mHeight / 6 + 10,
                mPaint);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("Time: " + mTime, 2 * mWidth / 3 + 15, mHeight / 6, mPaint);
        canvas.drawText("Distance: " + mDistanceCovered, 2 * mWidth / 3 + 15, mHeight / 6 + 40,
                mPaint);
        canvas.drawText("Speed: " + mCurrentSpeed, 2 * mWidth / 3 + 15, mHeight / 6 + 80, mPaint);
    }

    private void reDraw() {
        invalidate();
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }
    Runnable mAction = new Runnable() {
        @Override
        public void run() {
            mTime++;
            reDraw();
            mHandler.postDelayed(this, 1000);
        }
    };
}
