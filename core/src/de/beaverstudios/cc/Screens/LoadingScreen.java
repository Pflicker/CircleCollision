package de.beaverstudios.cc.Screens;

import com.badlogic.gdx.Screen;

import de.beaverstudios.cc.CC;
import de.beaverstudios.cc.Screens.GameScreen;

class LoadingScreen implements Screen {
    public LoadingScreen(CC cc) {
        cc.setScreen(new GameScreen(cc));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
