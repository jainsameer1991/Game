package com.chopsy.tictactoe;

public class Cell {

    private String mContent = "";

    public Cell(String content) {
        mContent = content;
    }

    public Cell(Peg peg) {
        mContent = "";
        if (peg == Peg.CROSS) {
            mContent = "X";
        } else {

            if (peg == Peg.NOUGHT) {
                mContent = "O";
            }
        }
    }

    public String getContent() {
        return mContent;
    }
}
