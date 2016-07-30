package com.tutorial.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jcs on 30/7/2016.
 */
public class InputHandler implements KeyListener {

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public InputHandler(Main main) {
        main.addKeyListener(this);
        main.setFocusable(true);
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if(e.getKeyCode() == KeyEvent.VK_W)
            up.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_S)
            down.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_A)
            left.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_D)
            right.toggle(pressed);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    class Key {

        private boolean down = false;

        void toggle(boolean pressed) {
            if(pressed != down) {
                down = pressed;
            }
        }

        public boolean isDown() {
            return down;
        }

    }
}
