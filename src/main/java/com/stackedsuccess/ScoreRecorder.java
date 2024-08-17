package com.stackedsuccess;

import java.io.FileWriter;
import java.io.IOException;

public class ScoreRecorder {
    private static final String scoreFile = "score.txt";

    public static void saveScore(String score) {
        FileWriter writer = new FileWriter(scoreFile)) {
            writer.write(score);
    }
}

