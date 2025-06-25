package com.de1.AngryBirds;

import java.io.Serializable;

public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;

    public int levelsCleared;
    public int[] highScores;

    public GameData(int levelsCleared, int[] highScores) {
        this.levelsCleared = levelsCleared;
        this.highScores = highScores;
    }
}
