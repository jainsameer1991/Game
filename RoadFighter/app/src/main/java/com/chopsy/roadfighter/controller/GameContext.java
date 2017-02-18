package com.chopsy.roadfighter.controller;

/**
 * This class helps in communication between individual controllers.
 */
public class GameContext {
    private static GameController mGameController;
    private static PlayerCarController mPlayerCarController;

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
}

