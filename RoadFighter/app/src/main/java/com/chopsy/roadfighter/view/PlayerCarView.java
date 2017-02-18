package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chopsy.roadfighter.controller.PlayerCarController;

public class PlayerCarView extends View implements View.OnTouchListener{

    private int mWidth;
    private int mHeight;
    private int left;
    private Paint mPaint;
    private Handler mHandler;
    private PlayerCarController mPlayerCarController;

    public PlayerCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPlayerCarController = new PlayerCarController(this);
        setOnTouchListener(this);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        left = mWidth / 3 + 5;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mWidth = w;
        mHeight = h;
        left = mWidth / 3 + 5;
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(left, mHeight - 110, left + 100, mHeight - 10, mPaint);
    }

    public void updateView(boolean turnLeft) {
        if (turnLeft) {
            left -= 10;
        } else {
            left += 10;
        }
        if (left <= mWidth / 3) {
            left = mWidth / 3;
        }
        if (left + 100 >= 2 * mWidth / 3) {
            left = 2 * mWidth / 3 - 100;
        }
        reDraw();
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
            mPlayerCarController.updateRoadView();
            mHandler.postDelayed(this, 500);
        }
    };
}
