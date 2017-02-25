package com.chopsy.tictactoe;

public enum Peg {
    NOUGHT, CROSS;

    public String toString(Peg peg) {
        return peg == Peg.CROSS ? "X" : "0";
    }
}
