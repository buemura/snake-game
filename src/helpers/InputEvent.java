package helpers;

import java.awt.event.KeyEvent;

public class InputEvent {

    public static boolean pressedUp(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W;
    }

    public static boolean pressedDown(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S;
    }

    public static boolean pressedLeft(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A;
    }

    public static boolean pressedRight(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D;
    }
}
