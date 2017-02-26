package com.chopsy.roadfighter.controller;

import android.graphics.Rect;

public class CollisionDetector {


    public boolean areIntersected(Rect firstRect, Rect secondRect) {
        return isHorizontallyIntersected(firstRect,
                secondRect) && isVerticallyIntersected(firstRect, secondRect);
    }

    private boolean isVerticallyIntersected(Rect firstRect, Rect secondRect) {
        return isFirsRectOnTopDuringIntersection(firstRect,
                secondRect) || isSecondRectOnTopDuringIntersection(firstRect, secondRect);
    }

    private boolean isSecondRectOnTopDuringIntersection(Rect firstRect, Rect secondRect) {
        return secondRect.top <= firstRect.top && firstRect.top <= secondRect.bottom && secondRect
                .bottom <= firstRect.bottom;
    }

    private boolean isFirsRectOnTopDuringIntersection(Rect firstRect, Rect secondRect) {
        return firstRect.top <= secondRect.top && secondRect.top <= firstRect.bottom && firstRect
                .bottom <= secondRect.bottom;
    }

    private boolean isHorizontallyIntersected(Rect firstRect, Rect secondRect) {

        return isFirstRectOnLeftDuringIntersection(firstRect, secondRect
        ) || isSecondRectOnLeftDuringIntersection(firstRect, secondRect);
    }

    private boolean isSecondRectOnLeftDuringIntersection(Rect firstRect, Rect secondRect) {
        return secondRect.left <= firstRect.left && firstRect.left <= secondRect.right && secondRect
                .right <= firstRect.right;
    }

    private boolean isFirstRectOnLeftDuringIntersection(Rect firstRect, Rect secondRect) {
        return firstRect.left <= secondRect.left && secondRect.left <= firstRect.right && firstRect
                .right <= secondRect.right;
    }
}
