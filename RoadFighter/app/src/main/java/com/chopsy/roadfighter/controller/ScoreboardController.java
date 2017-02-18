package com.chopsy.roadfighter.controller;

import com.chopsy.roadfighter.view.ScoreboardView;

public class ScoreboardController {

    private ScoreboardView mScoreboardView;

    public ScoreboardController(ScoreboardView scoreboardView){
        mScoreboardView = scoreboardView;
       GameContext.registerScoreboardController(this);
    }
}
