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
import com.chopsy.roadfighter.controller.PlayerCarController;

public class PlayerCarView extends AbstractDrawableView implements View.OnTouchListener {

   private int mRoadLeftEnd;
   private PlayerCarController mPlayerCarController;

    public PlayerCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPlayerCarController = new PlayerCarController(this);
        setOnTouchListener(this);
//        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mRoadLeftEnd = mWidth / 3 + 5;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mRoadLeftEnd = mWidth / 3 + 5;
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        Drawable playerCarDrawable = getResources().getDrawable(R.drawable.player_car);
        playerCarDrawable.setBounds(mRoadLeftEnd, mHeight - 200, mRoadLeftEnd + 100, mHeight - 50);
        playerCarDrawable.draw(canvas);
    }

    public void updateView(boolean turnLeft) {
        if (turnLeft) {
            mRoadLeftEnd -= 10;
        } else {
            mRoadLeftEnd += 10;
        }
        if (mRoadLeftEnd <= mWidth / 3) {
            mRoadLeftEnd = mWidth / 3;
        }
        if (mRoadLeftEnd + 100 >= 2 * mWidth / 3) {
            mRoadLeftEnd = 2 * mWidth / 3 - 100;
        }
        reDraw();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPlayerCarController.performActionDown()) return true;
                break;
            case MotionEvent.ACTION_UP:
                if (mPlayerCarController.performActionUp()) return true;
                break;
        }
        return true;
    }
}
