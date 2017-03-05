package com.chopsy.roadfighter.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.chopsy.roadfighter.model.PlayerCar;
import com.chopsy.roadfighter.model.RaceStatus;
import com.chopsy.roadfighter.view.CarsView;

public class PlayerCarController implements SensorEventListener, View.OnTouchListener {

    private CarsController mCarsController;
    private CarsView mPlayerCarView;
    private PlayerCar mPlayerCar;
    private Handler mPlayerCarSpeedHandler;
    private long timeInterval = 500;
    private SensorManager mSensorManager;


    public PlayerCarController(CarsController carsController, CarsView playerCarView) {
        mCarsController = carsController;
        mPlayerCarView = playerCarView;
        mPlayerCar = new PlayerCar();
        initSensorManager();
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
                    mPlayerCar.setSpeed(mPlayerCar.getSpeed() + 1);
                    mCarsController.updateBotCarSpeed(mPlayerCar.getSpeed() / 5 * 10);
                    mCarsController.updateScoreboardSpeed(mPlayerCar.getSpeed());
                }

                mCarsController.updateBotCarSpeed(mPlayerCar.getSpeed() / 5 * 10);
                mCarsController.updateBackground(mPlayerCar.getSpeed());
                mPlayerCar.setDistanceCovered(mPlayerCar.getDistanceCovered() + (mPlayerCar
                        .getSpeed() * 5));
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
                    mPlayerCar.setSpeed(0);
                    mCarsController.updateRoadView();
                    mCarsController.updateScoreboardSpeed(mPlayerCar.getSpeed());
                    mCarsController.updateBotCarSpeed(mPlayerCar.getSpeed() / 500 * 10);
                    mPlayerCarSpeedHandler = null;
                } else {
                    timeInterval += 20;
                    mPlayerCar.setDistanceCovered(mPlayerCar.getDistanceCovered() + (mPlayerCar
                            .getSpeed() * 20));
                    mPlayerCar.setSpeed(mPlayerCar.getSpeed() - 4);
                    if (mPlayerCar.getSpeed() < 1) {
                        mPlayerCar.setSpeed(1);
                    }
                    mCarsController.updateBotCarSpeed(mPlayerCar.getSpeed() / 5 * 10);
                    mCarsController.updateBackground(mPlayerCar.getSpeed());
                    mCarsController.updateScoreboardDistance();
                    mCarsController.updateRoadView();
                    mCarsController.updateScoreboardSpeed(mPlayerCar.getSpeed());
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
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) GameContext.getGameController().getSystemService
                    (Context
                            .SENSOR_SERVICE);
        }
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        if (mPlayerCarSpeedHandler == null) {
            mPlayerCarSpeedHandler = new Handler();
        }
        mPlayerCarSpeedHandler.postDelayed(decreasePlayerCarSpeedAction, timeInterval);
    }

    public int getDistanceCovered() {
        return mPlayerCar.getDistanceCovered();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING && event.sensor.getType() ==
                Sensor.TYPE_ACCELEROMETER) {
            if (Math.abs(event.values[0]) < 1) {
                return;
            }
            boolean turnLeft = event.values[0] > 0;
            mPlayerCarView.updatePlayerCarView(turnLeft);
            mPlayerCarView.reDraw();
            mCarsController.detectCollision();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startSensorManager() {
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                    .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void stopSensorManager() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    public void registerSensorManagerListener() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    public void stopListenersAndSensors() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    public void initSensorManager() {
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            mSensorManager = (SensorManager) GameContext.getGameController().getSystemService
                    (Context
                            .SENSOR_SERVICE);
        }
    }
}
