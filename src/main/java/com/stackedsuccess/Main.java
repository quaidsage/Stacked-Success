package com.stackedsuccess;

import com.stackedsuccess.SceneManager.AppUI;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  private static Scene scene;

  public static void main(String[] args) {
    launch();
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * Sets the root of the scene to the input AppUI.
   *
   * @param newUi The AppUI to set the scene to.
   */
  public static void setUi(AppUI newUi) {
    scene.setRoot(SceneManager.getScene(newUi));
    scene.getRoot().requestFocus();
  }

  /**
   * Sets the root of the scene to the input FXML file.
   *
   * @param fxml The name of the FXML file (without extension).
   * @throws IOException If the file is not found.
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    SceneManager.addScene(AppUI.MAIN_MENU, loadFxml("HomeScreen"));

    scene = new Scene(SceneManager.getScene(AppUI.MAIN_MENU), 1300, 900);
    scene.getStylesheets().add(getClass().getResource("/css/homescreen.css").toExternalForm());
    stage.setScene(scene);
    stage.setTitle("Stacked Success");
    stage.show();

    stage.setOnCloseRequest(
        event -> {
          System.exit(0);
        });
  }
}
