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

   private int left;
   private PlayerCarController mPlayerCarController;

    public PlayerCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPlayerCarController = new PlayerCarController(this);
        setOnTouchListener(this);
//        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        left = mWidth / 3 + 5;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        left = mWidth / 3 + 5;
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        Drawable playerCarDrawable = getResources().getDrawable(R.drawable.player_car);
        playerCarDrawable.setBounds(left, mHeight - 200, left + 100, mHeight - 50);
        playerCarDrawable.draw(canvas);

        Drawable enemyCarDrawable = getResources().getDrawable(R.drawable.enemy_car);
        enemyCarDrawable.setBounds(left, 50, left + 100, 200);
        enemyCarDrawable.draw(canvas);
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
