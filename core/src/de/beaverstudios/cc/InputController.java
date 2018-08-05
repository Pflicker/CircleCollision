package de.beaverstudios.cc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.awt.event.KeyEvent;

import de.beaverstudios.cc.ui.UI;

public class InputController implements InputProcessor {

    private GameScreen gameScreen;
    public float tL;
    public float tR;
    public float dtNext;
    public float time;

    public InputController(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
        this.tL         = 0.0f;
        this.tR         = 0.0f;
        this.dtNext     = 1.0f;
    }

    @Override
    public boolean keyDown(int keycode) {

        time = gameScreen.time;
        System.out.println("time " + time + " tR " + tR + " dt " + dtNext);
        if (Gdx.input.isKeyJustPressed(keycode)) {
            System.out.println(keycode);
            if (keycode == Input.Keys.D && time-tR > dtNext) {
                tR = time;
                gameScreen.moveRight();
                UI.moveRight.makeMove();
                System.out.println("Right");
            } else if (keycode == Input.Keys.A && time-tL > dtNext){
                tL = time;
                gameScreen.moveLeft();
                UI.moveLeft.makeMove();
                System.out.println("Left");
            }
        }
        return false;
    }


    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character)  {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        time = gameScreen.time;
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && time-tR > dtNext){
                tR = time;
                gameScreen.moveRight();
                UI.moveRight.makeMove();
                System.out.println("Right");
            } else if(time - tL > dtNext) {
                tL = time;
                gameScreen.moveLeft();
                UI.moveLeft.makeMove();
                System.out.println("Left");
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
