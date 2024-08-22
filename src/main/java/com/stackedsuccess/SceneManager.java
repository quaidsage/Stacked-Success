package com.stackedsuccess;

import java.util.*;
import javafx.scene.Parent;

public class SceneManager {
  public enum AppUI {
    MAIN_MENU,
    GAME,
  }

  private static HashMap<AppUI, Parent> sceneMap = new HashMap<AppUI, Parent>();

  /**
   * Adds a scene and its associated parent node to the scene map
   *
   * @param scene the AppUI scene to be added to the map
   * @param parent the Parent node associated with the scene
   */
  public static void addScene(AppUI scene, Parent parent) {
    sceneMap.put(scene, parent);
  }

  /**
   * Retrieves the parent node associated with a given scene.
   *
   * @param scene the AppUI scene whose associated parent node is to be retrieved.
   * @return the Parent node associated with the given scene
   */
  public static Parent getScene(AppUI scene) {
    return sceneMap.get(scene);
  }
}
