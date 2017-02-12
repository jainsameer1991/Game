package com.chopsy.tictactoe;

public class TicTacToeController {

    public static final int BOARD_SIZE = 3;
    Cell[][] mBoard;
    private boolean isFirstPlayerTurn = true;
    private TicTacToeView mView;
    private boolean isGameOn;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public TicTacToeController(TicTacToeView view) {
        mView = view;
        initBoard();
        isGameOn = true;
    }

    private void initBoard() {
        mBoard = new Cell[3][3];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                mBoard[i][j] = new Cell("");
            }
        }
    }

    public void nextMove(int row, int col) {
        if (isValidMove(row, col)) {
            if (isFirstPlayerTurn) {
                mBoard[row][col] = new Cell(Peg.CROSS);
            } else {
                mBoard[row][col] = new Cell(Peg.NOUGHT);
            }
            mView.reDraw();
            checkGameEndsOrNot();
            isFirstPlayerTurn = !isFirstPlayerTurn;
        } else {
            alertUser();
        }
    }

    private void checkGameEndsOrNot() {
        if (isGameOn) {
            checkRows();
        }
        if (isGameOn) {
            checkCols();
        }
        if (isGameOn) {
            checkDiagonals();
        }
    }

    private void checkDiagonals() {
        if (!mBoard[0][0].getContent().isEmpty() && mBoard[0][0].getContent().equalsIgnoreCase
                (mBoard[1][1].getContent()) && mBoard[1][1].getContent().equalsIgnoreCase
                (mBoard[2][2].getContent())) {
            startX = 0;
            startY = 0;
            endX = 2;
            endY = 2;
            terminateGame();
            return;
        }
        if (!mBoard[0][2].getContent().isEmpty() && mBoard[0][2].getContent().equalsIgnoreCase
                (mBoard[1][1].getContent()) && mBoard[1][1].getContent().equalsIgnoreCase
                (mBoard[2][0].getContent())) {
            startX = 0;
            startY = 2;
            endX = 2;
            endY = 0;
            terminateGame();
        }
    }

    private void checkCols() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            String content = mBoard[0][j].getContent();
            if (content.isEmpty()) {
                continue;
            }
            boolean isGameOver = true;
            for (int i = 1; i < BOARD_SIZE; i++) {
                if (!content.equalsIgnoreCase(mBoard[i][j].getContent())) {
                    isGameOver = false;
                    break;
                }
            }
            if (isGameOver) {
                startX = j;
                startY = 0;
                endX = j;
                endY = 2;
                terminateGame();
                break;
            }
        }
    }

    private void checkRows() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            String content = mBoard[i][0].getContent();
            if (content.isEmpty()) {
                continue;
            }
            boolean isGameOver = true;
            for (int j = 1; j < BOARD_SIZE; j++) {
                if (!content.equalsIgnoreCase(mBoard[i][j].getContent())) {
                    isGameOver = false;
                    break;
                }
            }
            if (isGameOver) {
                startX = 0;
                startY = i;
                endX = 2;
                endY = i;
                terminateGame();
                break;
            }
        }
    }

    private void terminateGame() {
        isGameOn = false;
        mView.reDraw();
    }

    private void alertUser() {

    }

    private boolean isValidMove(int row, int col) {
        return mBoard[row][col].getContent().isEmpty();
    }

    public Cell[][] getBoard() {
        return mBoard;
    }

    public boolean isGameOn() {
        return isGameOn;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void reStartGame() {
        initBoard();
        isGameOn = true;
        mView.reDraw();
    }
}
