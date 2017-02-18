package com.chopsy.roadfighter.controller;

/**
 * This class helps in communication between individual controllers.
 */
public class GameContext {
    private static GameController mGameController;
    private static PlayerCarController mPlayerCarController;
    private static RoadController mRoadController;

    public static void registerGameController(GameController gameController) {
        mGameController = gameController;
    }

    public static void registerPlayerCarController(PlayerCarController playerCarController) {
        mPlayerCarController = playerCarController;
    }

    public static GameController getGameController() {
        return mGameController;
    }

    public static PlayerCarController getPlayerCarController() {
        return mPlayerCarController;
    }

    public static void registerRoadController(RoadController roadController) {
        mRoadController = roadController;
    }

    public static RoadController getRoadController() {
        return mRoadController;
    }
}

