package com.chopsy.roadfighter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.chopsy.roadfighter.controller.CountdownController;
import com.chopsy.roadfighter.model.RaceStatus;

public class CountdownView extends AbstractDrawableView {

    private CountdownController mCountdownController;

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCountdownController = new CountdownController(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (CountdownController.getCurrentRaceStatus() == RaceStatus.NOT_START) {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(100f);
            canvas.drawText("" + mCountdownController.getTime(), getWidth() / 2, getHeight() / 2,
                    mPaint);
        }
    }
}
