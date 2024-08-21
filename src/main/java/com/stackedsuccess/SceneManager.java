package com.stackedsuccess;

import java.util.*;
import javafx.scene.Parent;

public class SceneManager {
  public enum AppUI {
    MAIN_MENU,
    GAME,
  }

  private static HashMap<AppUI, Parent> sceneMap = new HashMap<AppUI, Parent>();

  public static void addScene(AppUI scene, Parent parent) {
    sceneMap.put(scene, parent);
  }

  public static Parent getScene(AppUI scene) {
    return sceneMap.get(scene);
  }
}
