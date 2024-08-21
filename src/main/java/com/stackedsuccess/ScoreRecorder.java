package com.stackedsuccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoreRecorder {

  private ScoreRecorder() {
    throw new IllegalStateException();
  }

  private static final String SCOREFILE = "score.txt";
  private static final int MAX_SCORES = 12;

  /**
   * Save the score to the file and update the high score if necessary.
   *
   * @param score the score to save
   * @throws IOException if an I/O error occurs
   */
  public static void saveScore(String score) throws IOException {
    List<Integer> scores = getAllScores();
    scores.add(Integer.parseInt(score));
    Collections.sort(scores, Collections.reverseOrder());
    if (scores.size() > MAX_SCORES) {
      scores.remove(scores.size() - 1);
    }
    writeScores(scores);
  }

  /**
   * Get the high score as a string.
   *
   * @return the high score as a string
   * @throws IOException
   */
  public static String getHighScore() throws IOException {
    List<Integer> scores = getAllScores();
    if (scores.isEmpty()) {
      return "0";
    }
    return String.valueOf(scores.get(0));
  }

  /**
   * Get all scores from the file.
   *
   * @return a list of all scores
   * @throws IOException
   */
  private static List<Integer> getAllScores() throws IOException {
    List<Integer> scores = new ArrayList<>();
    try (BufferedReader buffread = new BufferedReader(new FileReader(SCOREFILE))) {
      String line;
      while ((line = buffread.readLine()) != null) {
        if (!line.trim().isEmpty()) { // Skip empty lines
          scores.add(Integer.parseInt(line));
        }
      }
    }
    return scores;
  }

  /**
   * Write the scores to the file.
   *
   * @param scores the scores to write
   * @throws IOException
   */
  private static void writeScores(List<Integer> scores) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCOREFILE))) {
      for (int score : scores) {
        writer.write(String.valueOf(score));
        writer.newLine();
      }
    }
  }

  /** Create a score file if it does not exist. */
  public static void createScoreFile() {
    File scoreFile = new File(SCOREFILE);
    if (!scoreFile.exists()) {
      try {
        boolean isFileCreated = scoreFile.createNewFile();
        if (!isFileCreated) {
          // Retry creating the file
          isFileCreated = scoreFile.createNewFile();
          if (!isFileCreated) {
            throw new IOException("Failed to create score file after retrying.");
          }
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("Creating score file", e);
      }
    }
  }
}
