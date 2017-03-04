package com.chopsy.roadfighter.controller;

import android.os.Handler;

import com.chopsy.roadfighter.model.RaceStatus;
import com.chopsy.roadfighter.view.CountdownView;

public class CountdownController {

    private int mTime = 3;
    private CountdownView mCountdownView;
    private Handler mTimeHandler;

    public CountdownController(CountdownView countdownView) {
        mCountdownView = countdownView;
        startController();
    }

    public int getTime() {
        return mTime;
    }

    public static RaceStatus getCurrentRaceStatus() {
        return GameContext.getCurrentRaceStatus();
    }

    public void startController() {
        mTime = 3;
        mTimeHandler = new Handler();
        Runnable decrementTimeAction = new Runnable() {
            @Override
            public void run() {
                mTime--;
                mCountdownView.reDraw();
                if (mTime > 0) {
                    mTimeHandler.postDelayed(this, 500);
                } else {
                    GameContext.setCurrentRaceStatus(RaceStatus.PLAYING);
                }
            }
        };
        mTimeHandler.postDelayed(decrementTimeAction, 500);
    }
}
