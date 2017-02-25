package com.chopsy.roadfighter.controller;

/**
 * This class helps in communication between individual controllers.
 */
public class GameContext {
    private static GameController mGameController;
    private static CarsController mCarsController;
    private static RoadController mRoadController;
    private static ScoreboardController mScoreboardController;

    public static void registerGameController(GameController gameController) {
        mGameController = gameController;
    }

    public static void registerPlayerCarController(CarsController carsController) {
        mCarsController = carsController;
    }

    public static GameController getGameController() {
        return mGameController;
    }

    public static CarsController getPlayerCarController() {
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
}

