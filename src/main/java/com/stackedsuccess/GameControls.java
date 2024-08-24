package com.stackedsuccess;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameControls {

  private final Map<Action, KeyCode> controls;

  public GameControls() {
    controls = new HashMap<>();
    initializeControls();
  }

  /**
   * Initialises the default key bindings for various actions in the game.
   *
   * <p>This method assigns specific keys to actions such as moving left, right,
   * down, performing a hard drop, rotating pieces, pausing the game, and holding
   * a piece</p>
   */
   private void initializeControls() {
    controls.put(Action.MOVE_LEFT, KeyCode.LEFT);
    controls.put(Action.MOVE_RIGHT, KeyCode.RIGHT);
    controls.put(Action.MOVE_DOWN, KeyCode.DOWN);
    controls.put(Action.HARD_DROP, KeyCode.SPACE);
    controls.put(Action.ROTATE_CLOCKWISE, KeyCode.Z);
    controls.put(Action.ROTATE_COUNTERCLOCKWISE, KeyCode.X);
    controls.put(Action.PAUSE, KeyCode.ESCAPE);
    controls.put(Action.HOLD, KeyCode.C);
  }

  /**
   * Assigns action to key, allowing rebinding of controls.
   *
   * @param action the Action to bind the key to
   * @param key the KeyCode that will perform the bound action
   */
  public void setControl(Action action, KeyCode key) {
    controls.put(action, key);
  }

  /**
   * Get associated Action from keyboard input.
   *
   * @param event the keyboard event captured
   * @return the bound action or null if not key not bound.
   */
  public Action getAction(KeyEvent event) {
    KeyCode key = event.getCode();
    if (controls.containsValue(key)) {
      return getKeyFromValue(controls, key);
    } else {
      return null;
    }
  }

  /**
   * Gets Action associated to specified keybinding.
   *
   * @param map the controls hashmap containing actions and associated keybindings
   * @param value the KeyCode from keyboard event
   * @return the Action associated with the keybinding given
   */
  private Action getKeyFromValue(Map<Action, KeyCode> map, KeyCode value) {
    for (Map.Entry<Action, KeyCode> entry : map.entrySet()) {
      if (entry.getValue().equals(value)) {
        return entry.getKey();
      }
    }
    return null;
  }
}
