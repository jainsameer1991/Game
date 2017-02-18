package com.chopsy.roadfighter.controller;

import android.os.Handler;

import com.chopsy.roadfighter.view.ScoreboardView;

public class ScoreboardController {

    private ScoreboardView mScoreboardView;
    private Handler mHandler;
    private int mTime;
    private int mDistanceCovered;
    private int mCurrentSpeed;

    public ScoreboardController(ScoreboardView scoreboardView) {
        mScoreboardView = scoreboardView;
        GameContext.registerScoreboardController(this);
        mHandler = new Handler();
        mHandler.postDelayed(mAction, 1000);
    }

    Runnable mAction = new Runnable() {
        @Override
        public void run() {
            mTime++;
            mScoreboardView.reDraw();
            mHandler.postDelayed(this, 1000);
        }
    };

    public int getTime() {
        return mTime;
    }

    public int getDistanceCovered() {
        return mDistanceCovered;
    }

    public int getCurrentSpeed() {
        return mCurrentSpeed;
    }
}
