package com.chopsy.roadfighter.controller;

import android.os.Handler;

import com.chopsy.roadfighter.view.ScoreboardView;

public class ScoreboardController {

    private ScoreboardView mScoreboardView;
    private Handler mTimeHandler;
    private int mTime;
    private int mDistanceCovered;
    private int mCurrentSpeed;

    public ScoreboardController(ScoreboardView scoreboardView) {
        mScoreboardView = scoreboardView;
        GameContext.registerScoreboardController(this);
    }

    public void startController() {
        mTimeHandler = new Handler();
        mTimeHandler.postDelayed(incrementTimeAction, 1000);
    }

    private Runnable incrementTimeAction = new Runnable() {
        @Override
        public void run() {
            mTime++;
            mScoreboardView.reDraw();
            mTimeHandler.postDelayed(this, 1000);
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

    public void setSpeed(int speed) {
        mCurrentSpeed = speed;
    }

    public void refresh() {
        mScoreboardView.reDraw();
    }

    public void setDistance(int distance) {
        mDistanceCovered = distance;
    }
}
