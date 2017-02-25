package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.chopsy.roadfighter.R;
import com.chopsy.roadfighter.controller.BotCarController;

public class BotCarView extends AbstractDrawableView {

    private BotCarController mBotCarController;
    private int mRoadLeftEnd;
    private int mCarYPos;

    public BotCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBotCarController = new BotCarController(this);
        mRoadLeftEnd = mWidth / 3 + 5;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mRoadLeftEnd = mWidth / 3 + 5;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Drawable enemyCarDrawable = getResources().getDrawable(R.drawable.enemy_car);
        enemyCarDrawable.setBounds(mRoadLeftEnd, mCarYPos + 50, mRoadLeftEnd + 100, mCarYPos + 200);
        enemyCarDrawable.draw(canvas);
    }

    public void setCarYPos(int carYPos) {
        mCarYPos = carYPos;
    }

    public int getCarYPos() {
        return mCarYPos;
    }
}
