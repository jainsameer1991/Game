package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.chopsy.roadfighter.controller.ScoreboardController;

public class ScoreboardView extends AbstractDrawableView {

    private ScoreboardController mScoreboardController;

    public ScoreboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScoreboardController = new ScoreboardController(this);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas) {

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        canvas.drawRect(2 * mWidth / 3 + 10, mHeight / 6 - 30, mWidth - 10, mHeight / 6 + 90,
                mPaint);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("Time: " + mScoreboardController.getTime(), 2 * mWidth / 3 + 15,
                mHeight / 6,
                mPaint);
        canvas.drawText("Distance: " + mScoreboardController.getDistanceCovered(),
                2 * mWidth / 3 + 15, mHeight / 6 + 40,
                mPaint);
        canvas.drawText("Speed: " + mScoreboardController.getCurrentSpeed(), 2 * mWidth / 3 + 15,
                mHeight / 6 + 80, mPaint);
    }
}


