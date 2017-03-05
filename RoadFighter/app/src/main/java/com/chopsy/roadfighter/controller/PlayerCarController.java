package com.chopsy.roadfighter.controller;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.chopsy.roadfighter.model.RaceStatus;
import com.chopsy.roadfighter.view.CarsView;

public class PlayerCarController implements View.OnTouchListener {

    private CarsController mCarsController;
    private CarsView mPlayerCarView;
    private Handler mPlayerCarSpeedHandler;
    private long timeInterval = 500;
    private int mPlayerCarSpeed;
    private int mDistanceCovered;

    public PlayerCarController(CarsController carsController, CarsView playerCarView) {
        mCarsController = carsController;
        mPlayerCarView = playerCarView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                performActionDown();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                performActionUp();
            }
            return true;
        }
        return false;
    }

    public void performActionUp() {
        if (mPlayerCarSpeedHandler == null) return;
        mPlayerCarSpeedHandler.removeCallbacks(increasePlayerCarSpeedAction);
        mPlayerCarSpeedHandler.postDelayed(decreasePlayerCarSpeedAction, timeInterval);
    }

    public void performActionDown() {
        if (mPlayerCarSpeedHandler != null) {
            mPlayerCarSpeedHandler.removeCallbacks(decreasePlayerCarSpeedAction);
            mPlayerCarSpeedHandler.postDelayed(increasePlayerCarSpeedAction, timeInterval);
            return;
        }
        mPlayerCarSpeedHandler = new Handler();
        mPlayerCarSpeedHandler.postDelayed(increasePlayerCarSpeedAction, timeInterval);
    }

    private Runnable increasePlayerCarSpeedAction = new Runnable() {
        @Override
        public void run() {

            if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
                if (timeInterval <= 1) {
                    timeInterval = 1;
                } else {
                    timeInterval -= 5;
                    mPlayerCarSpeed++;
                    mCarsController.updateBotCarSpeed(mPlayerCarSpeed / 5 * 10);
                    mCarsController.updateScoreboardSpeed(mPlayerCarSpeed);
                }

                mCarsController.updateBotCarSpeed(mPlayerCarSpeed / 5 * 10);
                mCarsController.updateBackground(mPlayerCarSpeed);
                mDistanceCovered += mPlayerCarSpeed * 5;
                mCarsController.updateScoreboardDistance();
                mPlayerCarSpeedHandler.postDelayed(this, timeInterval);
                mCarsController.updateRoadView();
            }

        }
    };

    private Runnable decreasePlayerCarSpeedAction = new Runnable() {
        @Override
        public void run() {

            if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
                if (timeInterval >= 500) {
                    timeInterval = 500;
                    mPlayerCarSpeedHandler.removeCallbacks(decreasePlayerCarSpeedAction);
                    mPlayerCarSpeedHandler.removeCallbacks(increasePlayerCarSpeedAction);
                    timeInterval = 500;
                    mPlayerCarSpeed = 0;
                    mCarsController.updateRoadView();
                    mCarsController.updateScoreboardSpeed(mPlayerCarSpeed);
                    mCarsController.updateBotCarSpeed(mPlayerCarSpeed / 500 * 10);
                    mPlayerCarSpeedHandler = null;
                } else {
                    timeInterval += 20;
                    mDistanceCovered += mPlayerCarSpeed * 20;
                    mPlayerCarSpeed -= 4;
                    if (mPlayerCarSpeed < 1) {
                        mPlayerCarSpeed = 1;
                    }
                    mCarsController.updateBotCarSpeed(mPlayerCarSpeed / 5 * 10);
                    mCarsController.updateBackground(mPlayerCarSpeed);
                    mCarsController.updateScoreboardDistance();
                    mCarsController.updateRoadView();
                    mCarsController.updateScoreboardSpeed(mPlayerCarSpeed);
                    mPlayerCarSpeedHandler.postDelayed(this, timeInterval);
                }
            }
        }
    };

    public void registerTouchListener() {
        mPlayerCarView.setOnTouchListener(this);
    }

    public void pause() {
        if (mPlayerCarSpeedHandler != null) {
            mPlayerCarSpeedHandler.removeCallbacks(increasePlayerCarSpeedAction);
            mPlayerCarSpeedHandler.removeCallbacks(decreasePlayerCarSpeedAction);
        }
    }

    public void resume() {
        if (mPlayerCarSpeedHandler == null) {
            mPlayerCarSpeedHandler = new Handler();
        }
        mPlayerCarSpeedHandler.postDelayed(decreasePlayerCarSpeedAction, timeInterval);
    }

    public int getDistanceCovered() {
        return mDistanceCovered;
    }
}
