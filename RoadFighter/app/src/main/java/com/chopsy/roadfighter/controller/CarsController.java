package com.chopsy.roadfighter.controller;

import android.graphics.Rect;
import android.os.Handler;

import com.chopsy.roadfighter.model.RaceStatus;
import com.chopsy.roadfighter.view.CarsView;

public class CarsController {

    private CarsView mCarsView;


    private ScoreboardController mScoreboardController;
    private GameController mGameController;
    private PlayerCarController mPlayerCarController;

    private CollisionDetector mCollisionDetector;
    private Handler mBotCarSpeedHandler;
    private int mRefreshCount = 0;

    private int mBotCurrentSpeed = 2;
    private final static int mBotMinSpeed = 2;

    public CarsController(CarsView carsView) {
        mCarsView = carsView;
        GameContext.registerCarsController(this);
        mPlayerCarController = new PlayerCarController(this, mCarsView);
        mPlayerCarController.initSensorManager();
        mCollisionDetector = new CollisionDetector();
    }

    public void start() {
        mGameController = GameContext.getGameController();
        mScoreboardController = GameContext.getScoreboardController();

        mPlayerCarController.initSensorManager();
        mPlayerCarController.startSensorManager();
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


    public void startSensorManager() {
        mPlayerCarController.startSensorManager();

    }

    public void stopSensorManager() {
        mPlayerCarController.stopSensorManager();

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

    protected void detectCollision() {
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
        mPlayerCarController.registerSensorManagerListener();

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
        mPlayerCarController.stopListenersAndSensors();

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
        return playerCarBounds != null && botCarBounds != null && mCollisionDetector
                .areIntersected(playerCarBounds, botCarBounds);
    }

    public void pause() {
        if (mBotCarSpeedHandler != null) {
            mBotCarSpeedHandler.removeCallbacks(botCarSpeedControlAction);
        }
        mPlayerCarController.pause();
        stopListenersAndSensors();
    }

    public void resume() {
        mPlayerCarController.resume();
        if (mBotCarSpeedHandler == null) {
            mBotCarSpeedHandler = new Handler();
        }
        mBotCarSpeedHandler.postDelayed(botCarSpeedControlAction, 1);
    }
}
