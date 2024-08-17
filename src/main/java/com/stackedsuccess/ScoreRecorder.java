package com.stackedsuccess;

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
}
