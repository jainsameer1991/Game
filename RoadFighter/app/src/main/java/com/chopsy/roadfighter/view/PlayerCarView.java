package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chopsy.roadfighter.R;
import com.chopsy.roadfighter.controller.PlayerCarController;

public class PlayerCarView extends View implements View.OnTouchListener {

    private int mWidth;
    private int mHeight;
    private int left;
    private Paint mPaint;
    private Handler mHandler;
    private PlayerCarController mPlayerCarController;
    private long timeInterval = 500;
    private int mSpeed = 0;
    private int distance = 0;
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
//        canvas.drawRect(left, mHeight - 110, left + 100, mHeight - 10, mPaint);
        Drawable playerCarDrawable = getResources().getDrawable(R.drawable.player_car);
        playerCarDrawable.setBounds(left, mHeight - 200, left + 100, mHeight - 50);
        playerCarDrawable.draw(canvas);

        Drawable enemyCarDrawable = getResources().getDrawable(R.drawable.enemy_car);
        enemyCarDrawable.setBounds(left, 50, left + 100, 200);
        enemyCarDrawable.draw(canvas);


//        canvas.drawPicture();
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
                if (mHandler != null) {
                    mHandler.removeCallbacks(decreaseSpeedAction);
//                    mHandler = null;
                    mHandler.postDelayed(increaseSpeedAction, timeInterval);
                    return true;
                }
                mHandler = new Handler();
                mHandler.postDelayed(increaseSpeedAction, timeInterval);
                break;
            case MotionEvent.ACTION_UP:
                if (mHandler == null) return true;
                mHandler.removeCallbacks(increaseSpeedAction);
                mHandler.postDelayed(decreaseSpeedAction, timeInterval);
//                mHandler = null;
                break;
        }
        return true;
    }

    Runnable increaseSpeedAction = new Runnable() {
        @Override
        public void run() {

            if (timeInterval <= 1) {
                timeInterval = 1;
//                mHandler.removeCallbacks(increaseSpeedAction);
//                mHandler = null;
            } else {
                timeInterval -= 5;
                mSpeed++;
                mPlayerCarController.updateScoreboardSpeed(mSpeed);

            }
            distance += mSpeed * 5;
            mPlayerCarController.updateBackground(mSpeed);
            mPlayerCarController.updateScoreboardDistance(distance);
            mHandler.postDelayed(this, timeInterval);
            mPlayerCarController.updateRoadView();
        }
    };

    Runnable decreaseSpeedAction = new Runnable() {
        @Override
        public void run() {


            if (timeInterval >= 500) {
                timeInterval = 500;
                mHandler.removeCallbacks(decreaseSpeedAction);
                mHandler.removeCallbacks(increaseSpeedAction);
                timeInterval = 500;
                mSpeed = 0;
                mPlayerCarController.updateRoadView();
                mPlayerCarController.updateScoreboardSpeed(mSpeed);
                mHandler = null;
            } else {
                timeInterval += 20;
                distance += mSpeed * 20;
                mSpeed -= 4;
                if (mSpeed < 1) {
                    mSpeed = 1;
                }
                mPlayerCarController.updateBackground(mSpeed);
                mPlayerCarController.updateScoreboardDistance(distance);
                mPlayerCarController.updateRoadView();
                mPlayerCarController.updateScoreboardSpeed(mSpeed);
                mHandler.postDelayed(this, timeInterval);
            }
        }
    };
}
