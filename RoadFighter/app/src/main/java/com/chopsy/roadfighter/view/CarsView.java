package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chopsy.roadfighter.R;
import com.chopsy.roadfighter.controller.CarsController;

public class CarsView extends AbstractDrawableView implements View.OnTouchListener {

    private int mBotCarLeftEnd;
    private int mPlayerCarLeftEnd;
    private int mPlayerCarTopEnd;
    private int mRoadLeftEnd;
    private int mRoadRightEnd;

    private int mBotCarYPos;

    private CarsController mCarsController;

    private static final int mCarWidth = 100;
    private static final int mCarHeight = 150;
    private static final int mPlayerCarDriftChange = 10;


    public CarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCarsController = new CarsController(this);
        setOnTouchListener(this);
        mPaint.setStyle(Paint.Style.STROKE);

        mRoadLeftEnd = mWidth / 3;
        mRoadRightEnd = 2 * mWidth / 3;

        mBotCarLeftEnd = mRoadLeftEnd + 5;

        mPlayerCarLeftEnd = mRoadLeftEnd + 5;
        mPlayerCarTopEnd = mHeight - 200;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mRoadLeftEnd = mWidth / 3;
        mRoadRightEnd = 2 * mWidth / 3;

        mBotCarLeftEnd = mRoadLeftEnd + 5;

        mPlayerCarLeftEnd = mRoadLeftEnd + 5;
        mPlayerCarTopEnd = mHeight - 200;
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        Drawable playerCarDrawable = getResources().getDrawable(R.drawable.player_car);
        playerCarDrawable.setBounds(mPlayerCarLeftEnd, mPlayerCarTopEnd,
                mPlayerCarLeftEnd + mCarWidth,
                mPlayerCarTopEnd + mCarHeight);
        playerCarDrawable.draw(canvas);

        Drawable enemyCarDrawable = getResources().getDrawable(R.drawable.enemy_car);
        enemyCarDrawable.setBounds(mBotCarLeftEnd, mBotCarYPos + 50, mBotCarLeftEnd + mCarWidth,
                mBotCarYPos + 50 + mCarHeight);
        enemyCarDrawable.draw(canvas);
    }

    public void updateView(boolean turnLeft) {
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
        reDraw();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mCarsController.performActionDown()) return true;
                break;
            case MotionEvent.ACTION_UP:
                if (mCarsController.performActionUp()) return true;
                break;
        }
        return true;
    }

    public void setBotCarYPos(int botCarYPos) {
        mBotCarYPos = botCarYPos;
    }

    public int getBotCarYPos() {
        return mBotCarYPos;
    }
}
