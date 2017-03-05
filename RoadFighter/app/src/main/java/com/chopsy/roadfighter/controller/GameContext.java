package com.chopsy.roadfighter.controller;

import android.util.Log;

import com.chopsy.roadfighter.model.RaceStatus;

/**
 * This class helps in communication between individual controllers.
 */
public class GameContext {
    private static GameController mGameController;
    private static CarsController mCarsController;
    private static RoadController mRoadController;
    private static ScoreboardController mScoreboardController;
    private static RaceStatus mOldRaceStatus = RaceStatus.NOT_START;
    private static RaceStatus mCurrentRaceStatus = RaceStatus.NOT_START;

    public static void registerGameController(GameController gameController) {
        mGameController = gameController;
    }

    public static void registerCarsController(CarsController carsController) {
        mCarsController = carsController;
    }

    public static GameController getGameController() {
        return mGameController;
    }

    public static CarsController getCarsController() {
        return mCarsController;
    }

    public static void registerRoadController(RoadController roadController) {
        mRoadController = roadController;
    }

    public static RoadController getRoadController() {
        return mRoadController;
    }

    public static void registerScoreboardController(ScoreboardController scoreboardController) {
        mScoreboardController = scoreboardController;
    }

    public static ScoreboardController getScoreboardController() {
        return mScoreboardController;
    }

    public static RaceStatus getCurrentRaceStatus() {
        return mCurrentRaceStatus;
    }

    public static void setCurrentRaceStatus(RaceStatus currentRaceStatus) {
        mOldRaceStatus = mCurrentRaceStatus;
        mCurrentRaceStatus = currentRaceStatus;
        if (!mOldRaceStatus.equals(mCurrentRaceStatus)) {
            doTransition();
        }
    }

    private static void doTransition() {
        Log.i("old ", mOldRaceStatus.toString());
        Log.i("current", mCurrentRaceStatus.toString());
        if (mOldRaceStatus == RaceStatus.NOT_START && mCurrentRaceStatus == RaceStatus.PLAYING) {
            mGameController.start();
            mScoreboardController.start();
            mCarsController.start();
        }
        if (mOldRaceStatus == RaceStatus.PLAYING && mCurrentRaceStatus == RaceStatus.PAUSE) {
            mCarsController.pause();
            mScoreboardController.pause();
        }
        if (mOldRaceStatus == RaceStatus.PAUSE && mCurrentRaceStatus == RaceStatus.PLAYING) {
            mCarsController.resume();
            mScoreboardController.resume();
        }
    }

    public static RaceStatus getRaceOldStatus() {
        return mOldRaceStatus;
    }
}

