package com.chopsy.roadfighter.controller;

import com.chopsy.roadfighter.view.BotCarView;

public class BotCarController {
    private BotCarView mBotCarView;
    private int mCurrentSpeed = 2;
    private final static int minSpeed = 2;

    public BotCarController(BotCarView botCarView) {
        mBotCarView = botCarView;
        GameContext.registerEnemyCarController(this);
        mCurrentSpeed = minSpeed;
    }

    public void updateCarPosition() {
        int distanceCovered = mCurrentSpeed * mBotCarView.getHeight() / (102*10) ;
        int carPos = mBotCarView.getCarYPos();
        carPos += distanceCovered;
        if (carPos > mBotCarView.getHeight()) {
            carPos = 0;
        }
        mBotCarView.setCarYPos(carPos);
        mBotCarView.reDraw();
    }

    public void updateSpeed(int playerCarSpeed) {
        mCurrentSpeed = minSpeed + playerCarSpeed;
    }
}
