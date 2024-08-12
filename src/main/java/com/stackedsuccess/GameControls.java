package com.stackedsuccess;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class GameControls {

    private final Map<Action, KeyCode> controls;

    public GameControls() {
        controls = new HashMap<>();
        initializeControls();
    }

    /** Bind default controls to actions. */
    private void initializeControls() {
        controls.put(Action.MOVE_LEFT, KeyCode.A);
        controls.put(Action.MOVE_RIGHT, KeyCode.D);
        controls.put(Action.MOVE_DOWN, KeyCode.S);
        controls.put(Action.ROTATE_CLOCKWISE, KeyCode.Q);
        controls.put(Action.ROTATE_COUNTERCLOCKWISE, KeyCode.E);
        controls.put(Action.PAUSE, KeyCode.ESCAPE);
    }

    /**
     * Assigns action to key, allowing rebinding of controls.
     *
     * @param action the Action to bind the key to
     * @param key the KeyCode that will perform the bound action
     */
    public void setControl(Action action, KeyCode key) { controls.put(action, key); }

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
