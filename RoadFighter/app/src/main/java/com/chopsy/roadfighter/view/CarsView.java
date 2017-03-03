package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.chopsy.roadfighter.R;
import com.chopsy.roadfighter.controller.CarsController;

public class CarsView extends AbstractDrawableView {

    private int mBotCarLeftEnd;
    private int mBotCarTopEnd;

    private int mPlayerCarLeftEnd;
    private int mPlayerCarTopEnd;

    private int mRoadLeftEnd;
    private int mRoadRightEnd;

    private static final int mCarWidth = 100;
    private static final int mCarHeight = 150;

    private static final int mPlayerCarDriftChange = 10;

    public static boolean isPlayerCarVisible = true;


    public CarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        new CarsController(this);

        mRoadLeftEnd = mWidth / 3;
        mRoadRightEnd = 2 * mWidth / 3;

        mBotCarLeftEnd = mRoadLeftEnd + 5;

        mPlayerCarLeftEnd = mRoadLeftEnd + 5;
        mPlayerCarTopEnd = mHeight - 200;
        mBotCarTopEnd = -mCarHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mRoadLeftEnd = mWidth / 3;
        mRoadRightEnd = 2 * mWidth / 3;

        mBotCarLeftEnd = mRoadLeftEnd + 5;
        mBotCarTopEnd = -mCarHeight;

        mPlayerCarLeftEnd = mRoadLeftEnd + 5;
        mPlayerCarTopEnd = mHeight - 200;
    }

    @Override
    public void onDraw(Canvas canvas) {

        drawPlayerCar(canvas);
        drawBotCar(canvas);
    }

    private void drawBotCar(Canvas canvas) {
        Drawable botCarDrawable = getResources().getDrawable(R.drawable.bot_car);
        botCarDrawable.setBounds(mBotCarLeftEnd, mBotCarTopEnd, mBotCarLeftEnd + mCarWidth,
                mBotCarTopEnd + mCarHeight);
        botCarDrawable.draw(canvas);
    }

    private void drawPlayerCar(Canvas canvas) {

        if(isPlayerCarVisible){
            Drawable playerCarDrawable = getResources().getDrawable(R.drawable.player_car);
            playerCarDrawable.setBounds(mPlayerCarLeftEnd, mPlayerCarTopEnd,
                    mPlayerCarLeftEnd + mCarWidth,
                    mPlayerCarTopEnd + mCarHeight);
            playerCarDrawable.draw(canvas);
        }
    }

    public void updatePlayerCarView(boolean turnLeft) {
        if (turnLeft) {
            mPlayerCarLeftEnd -= mPlayerCarDriftChange;
        } else {
            mPlayerCarLeftEnd += mPlayerCarDriftChange;
        }
        if (mPlayerCarLeftEnd <= mRoadLeftEnd) {
            mPlayerCarLeftEnd = mRoadLeftEnd;
        }
        if (mPlayerCarLeftEnd + mCarWidth >= mRoadRightEnd) {
            mPlayerCarLeftEnd = mRoadRightEnd - mCarWidth;
        }
    }

    public void setBotCarTopEnd(int botCarTopEnd) {
        mBotCarTopEnd = botCarTopEnd;
    }

    public int getBotCarTopEnd() {
        return mBotCarTopEnd;
    }

    public Rect getPlayerCarBounds() {
        return new Rect(mPlayerCarLeftEnd, mPlayerCarTopEnd, mPlayerCarLeftEnd + mCarWidth,
                mPlayerCarTopEnd + mCarHeight);
    }

    public Rect getBotCarBounds() {
        return new Rect(mBotCarLeftEnd, mBotCarTopEnd, mBotCarLeftEnd + mCarWidth,
                mBotCarTopEnd + mCarHeight);
    }

    public static int getCarHeight() {
        return mCarHeight;
    }
}
