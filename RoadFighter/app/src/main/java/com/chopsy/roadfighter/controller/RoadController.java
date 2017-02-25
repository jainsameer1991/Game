package com.chopsy.roadfighter.controller;

import com.chopsy.roadfighter.view.RoadView;

public class RoadController {

    private RoadView mRoadView;

    public RoadController(RoadView roadView) {
        mRoadView = roadView;
        GameContext.registerRoadController(this);
    }

    public void updateRoadView() {
        mRoadView.updateRoadView();
    }
}
