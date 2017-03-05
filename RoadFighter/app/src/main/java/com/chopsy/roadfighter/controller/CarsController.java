package com.chopsy.roadfighter.controller;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import com.chopsy.roadfighter.model.RaceStatus;
import com.chopsy.roadfighter.view.CarsView;

public class CarsController implements SensorEventListener {

    private CarsView mCarsView;
    private SensorManager mSensorManager;

    private ScoreboardController mScoreboardController;
    private GameController mGameController;
    private PlayerCarController mPlayerCarController;

    private CollisionDetector mCollisionDetector;
    private Handler mPlayerCarSpeedHandler;
    private Handler mBotCarSpeedHandler;
    private int mRefreshCount = 0;

    private int mBotCurrentSpeed = 2;
    private final static int mBotMinSpeed = 2;

    public CarsController(CarsView carsView) {
        mCarsView = carsView;
        GameContext.registerPlayerCarController(this);
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            mSensorManager = (SensorManager) mGameController.getSystemService(Context
                    .SENSOR_SERVICE);
        }
        mPlayerCarController = new PlayerCarController(this, mCarsView);
    }

    public void start() {
        mGameController = GameContext.getGameController();
        mScoreboardController = GameContext.getScoreboardController();
        mCollisionDetector = new CollisionDetector();
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            mSensorManager = (SensorManager) mGameController.getSystemService(Context
                    .SENSOR_SERVICE);
        }
        startSensorManager();
        mPlayerCarController.registerTouchListener();

        mBotCurrentSpeed = mBotMinSpeed;
        mBotCarSpeedHandler = new Handler();
        mBotCarSpeedHandler.postDelayed(botCarSpeedControlAction, 1000);
    }

    private Runnable botCarSpeedControlAction = new Runnable() {
        @Override
        public void run() {
            updateBotCarPosition();
            mBotCarSpeedHandler.postDelayed(this, 10);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING && event.sensor.getType() ==
                Sensor.TYPE_ACCELEROMETER) {
            if (Math.abs(event.values[0]) < 1) {
                return;
            }
            boolean turnLeft = event.values[0] > 0;
            mCarsView.updatePlayerCarView(turnLeft);
            mCarsView.reDraw();
            detectCollision();
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

    public void updateRoadView() {
        GameContext.getRoadController().updateRoadView();
    }

    public void updateScoreboardSpeed(int speed) {

        mScoreboardController.setSpeed(speed);
    }

    public void updateBackground(int speed) {
        mGameController.updateBackground(speed);
    }

    public void updateScoreboardDistance() {
        mScoreboardController.setDistance(mPlayerCarController.getDistanceCovered());
        mScoreboardController.refresh();
    }

    private Runnable refreshCarAction = new Runnable() {
        @Override
        public void run() {
            CarsView.isPlayerCarVisible = !CarsView.isPlayerCarVisible;
            mRefreshCount++;
            if (mRefreshCount < 7) {
                mCarsView.reDraw();
                mBotCarSpeedHandler.postDelayed(this, 100);
            } else {
                CarsView.isPlayerCarVisible = true;
                mRefreshCount = 0;
            }
        }
    };

    protected void updateBotCarSpeed(int playerCarSpeed) {
        mBotCurrentSpeed = mBotMinSpeed + playerCarSpeed;
    }

    private void stopBotCar() {
        mBotCurrentSpeed = 0;
    }

    public void updateBotCarPosition() {
        int distanceCovered = mBotCurrentSpeed * mCarsView.getHeight() / (102 * 10);
        int carPos = mCarsView.getBotCarTopEnd();
        carPos += distanceCovered;
        if (carPos > mCarsView.getHeight()) {
            carPos = 0;
            changeBotCarLeftEndPosition();
        }
        mCarsView.setBotCarTopEnd(carPos);
        mCarsView.reDraw();
        detectCollision();
    }

    private void changeBotCarLeftEndPosition() {
        int roadLeftEnd = mCarsView.getRoadLeftEnd();
        int roadRightEnd = mCarsView.getRoadRightEnd();
        int carWidth = CarsView.getCarWidth();
        int botCarNewLeftPosition = (int) (Math.random() * (roadRightEnd - roadLeftEnd -
                carWidth) + roadLeftEnd);
        mCarsView.setBotCarLeftEnd(botCarNewLeftPosition);
    }

    private void detectCollision() {
        if (isCollisionHappens()) {
            resetGame();
            mCarsView.reDraw();
        }
    }

    private void resetGame() {
        resetEverythingExceptPlayerCar();
        stopListenersAndSensors();
        refreshPlayerCar();
        resumeGameAfterRefresh();
    }

    private void resumeGameAfterRefresh() {
        GameContext.setCurrentRaceStatus(RaceStatus.PLAYING);
        mBotCurrentSpeed = mBotMinSpeed;
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    private void refreshPlayerCar() {
        Handler mRefreshPlayerCarHandler = new Handler();
        mRefreshCount = 0;
        mRefreshPlayerCarHandler.postDelayed(refreshCarAction, 100);
        mCarsView.reDraw();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stopListenersAndSensors() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    private void resetEverythingExceptPlayerCar() {
        GameContext.setCurrentRaceStatus(RaceStatus.PAUSE);
        mCarsView.setBotCarTopEnd(-1 * CarsView.getCarHeight());
        stopBotCar();
        updateScoreboardSpeed(0);
    }


    private boolean isCollisionHappens() {
        Rect playerCarBounds = mCarsView.getPlayerCarBounds();
        Rect botCarBounds = mCarsView.getBotCarBounds();
        return mCollisionDetector.areIntersected(playerCarBounds, botCarBounds);
    }

    public void pause() {
        if (mBotCarSpeedHandler != null) {
            mBotCarSpeedHandler.removeCallbacks(botCarSpeedControlAction);
        }
        mPlayerCarController.pause();
        stopListenersAndSensors();
    }

    public void resume() {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) mGameController.getSystemService(Context
                    .SENSOR_SERVICE);
        }
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mBotCarSpeedHandler.postDelayed(botCarSpeedControlAction, 1);
        if (mPlayerCarSpeedHandler == null) {
            mPlayerCarSpeedHandler = new Handler();
        }
        mPlayerCarController.resume();
    }
}
