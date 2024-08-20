package com.stackedsuccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreRecorder {

  private ScoreRecorder() {
    throw new IllegalStateException();
  }

  private static final String SCOREFILE = "score.txt";

  public static void saveScore(String score) throws IOException {
    FileWriter writer = new FileWriter(SCOREFILE);
    writer.write(score);
    writer.close();
  }

  public static int getHighScoreAsInt() throws IOException {
    return Integer.parseInt(getHighScore());
  }

  public static boolean isHighScore (String score) throws IOException {
    int currentScore = Integer.parseInt(score);
    int highScore = getHighScoreAsInt();
    return currentScore > highScore;
  }

  public static String getHighScore() throws IOException {
    FileReader reader = new FileReader(SCOREFILE);
    BufferedReader buffread = new BufferedReader(reader);
    String highScore = buffread.readLine();
    buffread.close();
    return highScore;
  }
}
