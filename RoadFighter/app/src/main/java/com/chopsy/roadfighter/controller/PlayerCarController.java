package com.chopsy.roadfighter.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import com.chopsy.roadfighter.view.PlayerCarView;

public class PlayerCarController implements SensorEventListener {

    private PlayerCarView mPlayerCarView;
    private SensorManager mSensorManager;
    private ScoreboardController mScoreboardController;
    private GameController mGameController;
    private Handler mHandler;
    private long timeInterval = 500;
    private int mSpeed = 0;
    private int distance = 0;


    public PlayerCarController(PlayerCarView playerCarView) {
        mPlayerCarView = playerCarView;
        GameContext.registerPlayerCarController(this);
        mGameController = GameContext.getGameController();
        mSensorManager = (SensorManager) mGameController.getSystemService(Context
                .SENSOR_SERVICE);
        mScoreboardController = GameContext.getScoreboardController();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (Math.abs(event.values[0]) < 1) {
                return;
            }
            boolean turnLeft = event.values[0] > 0;
            mPlayerCarView.updateView(turnLeft);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startSensorManager() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    public void stopSensorManager() {
        mSensorManager.unregisterListener(this);
    }

    public void updateRoadView() {
        GameContext.getRoadController().updateRoadView();
    }

    public void updateScoreboardSpeed(int speed) {

        mScoreboardController.setSpeed(speed);
    }

    public void updateBackground(int speed) {
        mGameController.updateBackground(speed);
    }

    public void updateScoreboardDistance(int distance) {
        mScoreboardController.setDistance(distance);
        mScoreboardController.refresh();
    }

    public boolean performActionUp() {
        if (mHandler == null) return true;
        mHandler.removeCallbacks(increaseSpeedAction);
        mHandler.postDelayed(decreaseSpeedAction, timeInterval);
        return false;
    }

    public boolean performActionDown() {
        if (mHandler != null) {
            mHandler.removeCallbacks(decreaseSpeedAction);
//                    mHandler = null;
            mHandler.postDelayed(increaseSpeedAction, timeInterval);
            return true;
        }
        mHandler = new Handler();
        mHandler.postDelayed(increaseSpeedAction, timeInterval);
        return false;
    }

    Runnable increaseSpeedAction = new Runnable() {
        @Override
        public void run() {

            if (timeInterval <= 1) {
                timeInterval = 1;
//                mHandler.removeCallbacks(increaseSpeedAction);
//                mHandler = null;
            } else {
                timeInterval -= 5;
                mSpeed++;
                updateScoreboardSpeed(mSpeed);

            }
            distance += mSpeed * 5;
            updateBackground(mSpeed);
            updateScoreboardDistance(distance);
            mHandler.postDelayed(this, timeInterval);
            updateRoadView();
        }
    };

    Runnable decreaseSpeedAction = new Runnable() {
        @Override
        public void run() {


            if (timeInterval >= 500) {
                timeInterval = 500;
                mHandler.removeCallbacks(decreaseSpeedAction);
                mHandler.removeCallbacks(increaseSpeedAction);
                timeInterval = 500;
                mSpeed = 0;
                updateRoadView();
                updateScoreboardSpeed(mSpeed);
                mHandler = null;
            } else {
                timeInterval += 20;
                distance += mSpeed * 20;
                mSpeed -= 4;
                if (mSpeed < 1) {
                    mSpeed = 1;
                }
                updateBackground(mSpeed);
                updateScoreboardDistance(distance);
                updateRoadView();
                updateScoreboardSpeed(mSpeed);
                mHandler.postDelayed(this, timeInterval);
            }
        }
    };
}
