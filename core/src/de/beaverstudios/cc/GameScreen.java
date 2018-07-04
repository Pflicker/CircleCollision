package de.beaverstudios.cc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

class GameScreen implements Screen {

    public Game game;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    public UI ui;

    public InputMultiplexer inputMultiplexer;
    public InputController ip;

    public GameScreen(CC cc) {
        this.game = cc;
        this.batch = cc.gameBatch;

        ui = new UI(game,batch);

        shapeRenderer = new ShapeRenderer();

        ip = new InputController();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(ui);
        inputMultiplexer.addProcessor(ip);


        Gdx.input.setInputProcessor(inputMultiplexer);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(1, 1, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*shapeRenderer.begin();
        shapeRenderer.box(10,10,0,100,100,100);
        shapeRenderer.end();*/

        ui.draw();

    }

    public void update(float dt){

        ui.act();

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
