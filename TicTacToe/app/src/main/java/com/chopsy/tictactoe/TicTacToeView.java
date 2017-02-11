package com.chopsy.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TicTacToeView extends View implements View.OnTouchListener {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private TicTacToeController mController;


    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mController = new TicTacToeController(this);
        mPaint = new Paint();

        setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mWidth = w;
        mHeight = h;
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {

        clearCanvas(canvas);
        drawEmptyBoard(canvas);
        drawCurrentBoardState(canvas);
        drawGameTerminatingLines(canvas);
    }

    private void drawGameTerminatingLines(Canvas canvas) {
        if (mController.isGameOn()) {
            return;
        }
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        int startX = mController.getStartX();
        int startY = mController.getStartY();
        int endX = mController.getEndX();
        int endY = mController.getEndY();
        if (checkDiagonalsThenDraw(canvas, startX, startY, endX, endY)){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mController.reStartGame();
//            mController = new TicTacToeController(this);
            return;
        }
        if (startX == endX) { // check cols
            canvas.drawLine(startX * getBlockWidth() + getBlockWidth() / 2, 0,
                    endX * getBlockWidth() + getBlockWidth() / 2,
                    getHeight(), mPaint);
        } else { // check rows
            canvas.drawLine(0, startY * getBlockHeight() + getBlockHeight() / 2, getWidth(),
                    endY * getBlockHeight() + getBlockHeight() / 2,
                    mPaint);
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mController.reStartGame();
//        mController = new TicTacToeController(this);
    }

    private boolean checkDiagonalsThenDraw(Canvas canvas, int startX, int startY, int endX,
                                           int endY) {
        if (startX == 0 && startY == 0 && endX == 2 && endY == 2) {
            canvas.drawLine(0, 0, getWidth(), getHeight(), mPaint);
            return true;
        }
        if (startX == 0 && startY == 2 && endX == 2 && endY == 0) {
            canvas.drawLine(0, getHeight(), getWidth(), 0, mPaint);
            return true;
        }
        return false;
    }

    protected void reDraw() {
        this.invalidate();
    }

    private void drawCurrentBoardState(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        int textSize = Math.min(getBlockWidth(), getBlockHeight()) - 20;
        mPaint.setTextSize(textSize);
        Cell[][] board = mController.getBoard();
        for (int i = 0; i < TicTacToeController.BOARD_SIZE; i++) {
            for (int j = 0; j < TicTacToeController.BOARD_SIZE; j++) {
                String content = board[i][j].getContent();
                if (content.isEmpty()) {
                    continue;
                }
                Rect textBounds = new Rect();
                mPaint.getTextBounds(content, 0, content.length(), textBounds);

                int x = calcPegXPosition(j, textBounds);
                int y = calcPegYPosition(i, textBounds);
                if ("X".equalsIgnoreCase(content)) {
                    mPaint.setColor(Color.BLUE);
                } else {
                    if ("O".equalsIgnoreCase(content)) {
                        mPaint.setColor(Color.GREEN);
                    }
                }
                canvas.drawText(content, x, y, mPaint);
            }
        }
        mPaint.setColor(Color.BLACK);
    }

    private int calcPegXPosition(int j, Rect rect) {
        int blockLineXCoordinate = j * getBlockWidth();
        int blockLineXOneCoordinate = (j + 1) * getBlockWidth();
        return blockLineXCoordinate + (blockLineXOneCoordinate - blockLineXCoordinate - rect
                .width()) / 2;
    }

    private int calcPegYPosition(int i, Rect rect) {
        int blockLineYCoordinate = (i) * getBlockHeight();
        int blockLineYOneCoordinate = (i + 1) * getBlockHeight();
        int centerYPosition = (blockLineYOneCoordinate - blockLineYCoordinate + rect
                .height()) / 2;
        return blockLineYCoordinate + centerYPosition;
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
    }

    private void drawEmptyBoard(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        canvas.drawLine(0, getBlockHeight(), mWidth, getBlockHeight(), mPaint);
        canvas.drawLine(0, 2 * getBlockHeight(), mWidth, 2 * getBlockHeight(), mPaint);
        canvas.drawLine(getBlockWidth(), 0, getBlockWidth(), mHeight, mPaint);
        canvas.drawLine(2 * getBlockWidth(), 0, 2 * getBlockWidth(), mHeight, mPaint);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int col = x / getBlockWidth();
        int row = y / getBlockHeight();
        mController.nextMove(row, col);
        return true;
    }

    private int getBlockWidth() {
        return mWidth / 3;
    }

    private int getBlockHeight() {
        return mHeight / 3;
    }
}
