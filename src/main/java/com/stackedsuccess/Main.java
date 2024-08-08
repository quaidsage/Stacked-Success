package com.stackedsuccess;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public enum SceneName {
        MENU
    }

    private static Map<SceneName, Parent> scenes = new HashMap<>();

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
     * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
     *
     * @param stage The primary stage of the application.
     * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        scenes.put(SceneName.MENU, loadFxml("GameScreen"));
        Parent root = scenes.get(SceneName.MENU);
        scene = new Scene(root, 1400, 800);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

}