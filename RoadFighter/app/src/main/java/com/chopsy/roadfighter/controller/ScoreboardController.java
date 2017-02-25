package com.chopsy.roadfighter.controller;

import android.os.Handler;

import com.chopsy.roadfighter.view.ScoreboardView;

public class ScoreboardController {

    private ScoreboardView mScoreboardView;
    private Handler mHandler;
    private int mTime;
    private int mDistanceCovered;
    private int mCurrentSpeed;

    private BotCarController mBotCarController;

    public ScoreboardController(ScoreboardView scoreboardView) {
        mScoreboardView = scoreboardView;
        GameContext.registerScoreboardController(this);
        mBotCarController = GameContext.getEnemyCarController();
        mHandler = new Handler();
        mHandler.postDelayed(mAction, 1000);
    }

    Runnable mAction = new Runnable() {
        @Override
        public void run() {
            mTime++;
            mScoreboardView.reDraw();
            mBotCarController.updateCarPosition();
            mHandler.postDelayed(this, 10);
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
