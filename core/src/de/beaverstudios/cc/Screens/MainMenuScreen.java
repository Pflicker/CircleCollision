package de.beaverstudios.cc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.beaverstudios.cc.CC;

public class MainMenuScreen implements Screen, InputProcessor {

    private Skin skin;
    private Stage stage;

    private Sprite sprite;
    private SpriteBatch batch;
    private Viewport menuPort;
    private final CC game;



    public MainMenuScreen(CC cc) {
        this.game = cc;

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        menuPort = new FillViewport(CC.WIDTH,CC.HEIGHT);
        stage = new Stage(menuPort);

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());
        table.columnDefaults(0);
        table.columnDefaults(1);
        table.columnDefaults(2);

        TextButton btnStartMatch = new TextButton("Quick Match", skin);
        TextButton btnOptions = new TextButton("Options", skin);
        TextButton btnQuit = new TextButton("Quit Game", skin);

        btnStartMatch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Clicked button", "Yep, you did");
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        });

        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Clicked button", "Options");
                //game.setScreen(new de.tooleffects.game.screens.OptionScreen(game));
                dispose();
            }
        });

        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Clicked button", "Quit");
                event.stop();
                Gdx.app.exit();
            }
        });

        table.add(btnStartMatch).padBottom(30);
        table.row();
        table.add(btnOptions).padBottom(30);
        table.row();
        table.add(btnQuit);
        table.debug();

        stage.addActor(table);

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        InputMultiplexer im = new InputMultiplexer(stage,this);
        Gdx.input.setInputProcessor(im);

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
