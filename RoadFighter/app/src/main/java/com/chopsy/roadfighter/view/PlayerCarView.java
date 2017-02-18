package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.chopsy.roadfighter.controller.PlayerCarController;

public class PlayerCarView extends View {

    private int mWidth;
    private int mHeight;
    private int left;
    private Paint mPaint;
    private PlayerCarController mPlayerCarController;

    public PlayerCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPlayerCarController = new PlayerCarController(this);
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
}
