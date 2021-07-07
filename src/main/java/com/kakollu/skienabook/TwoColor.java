package com.kakollu.skienabook;

public enum TwoColor {
    UNCOLORED,
    BLACK,
    WHITE;

    public static TwoColor complement(TwoColor color) {
        if (color == BLACK) return WHITE;
        if (color == WHITE) return BLACK;
        return UNCOLORED;
    }
}
