package de.beaverstudios.cc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import de.beaverstudios.cc.Box2D.B2dModel;
import de.beaverstudios.cc.ui.UI;

public class GameScreen implements Screen {

    public Game game;

    public SpriteBatch batch;
    public PolygonSpriteBatch polyBatch;

    public UI ui;

    public InputMultiplexer inputMultiplexer;
    public InputController ip;

    public static Array<Integer> dirLeft;
    public static Array<Integer> dirRight;

    public OrthographicCamera cam;
    public B2dModel model;
    public Box2DDebugRenderer debugRenderer;

    public static float time;
    public static int score;

    public static boolean play = true;

    public Texture player;
    public Sprite spPlayer;
    public Texture asteroid;
    public PolygonSprite poly;
    public Texture background;

    public float x,y;
    public float r;

    public GameScreen(CC cc) {
        this.game = cc;
        this.batch = cc.gameBatch;

        polyBatch = new PolygonSpriteBatch();

        dirLeft = new Array<Integer>(3);
        dirRight = new Array<Integer>(3);

        for (int i = 0; i < 3; i++){
            createDirLeft();
            createDirRight();
        }

        ui = new UI(game,batch);

        ip = new InputController(this);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(ui);
        inputMultiplexer.addProcessor(ip);

        Gdx.input.setInputProcessor(inputMultiplexer);

        model = new B2dModel();
        cam = new OrthographicCamera(32,24);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        score = 0;
        time = 0;

        x = Gdx.graphics.getWidth()/2;
        y = Gdx.graphics.getHeight()/2;
        r = 0;

        background = new Texture(Gdx.files.internal("background.jpg"));

        player = new Texture(Gdx.files.internal("player2.png"));
        spPlayer = new Sprite(player);
        spPlayer.scale(0.01f);
        spPlayer.setPosition(300,300);

        asteroid = new Texture(Gdx.files.internal("asteroiden.png"));
        PolygonRegion region = new PolygonRegion(new TextureRegion(asteroid),
                new float[]{
                        0, 10,
                        85, 9,
                        85, 120,
                        0, 120
                }, new short[]{
                0, 1, 2,
                0, 2, 3
        });
        poly = new PolygonSprite(region);
        poly.setOrigin(10,20);




    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        update(dt);

        model.logicStep(dt);
        Gdx.gl.glClearColor(1, 1, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);

        batch.begin();
        batch.draw(background,0,0);
        batch.draw(player,x,y,50,50,100,100,1,1,r,1,1,player.getWidth(),player.getHeight(),false,false);
        batch.end();

        polyBatch.begin();
        poly.draw(polyBatch);
        polyBatch.end();

        ui.draw();

    }

    public void update(float dt){

        if(play) {
            time += dt;
            score += dt;


            float dx = (float) Math.cos(r) * 50;
            float dy = (float) Math.sin(r) * 50;

            x += dx * dt;
            y += dy * dt;

        }
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
    public void changeRotation(int ir){
        this.r = ir;
    }

    public void moveLeft() {
        changeRotation(dirLeft.get(0));
        dirLeft.removeIndex(0);
        createDirLeft();
    }

    public void moveRight() {
        changeRotation(dirRight.get(0));
        dirRight.removeIndex(0);
        createDirRight();
    }

    public void createDirLeft(){
        int r = (int) (Math.random() * 180 + 180);
        dirLeft.add(r);
        System.out.println(dirLeft);
    }

    public void createDirRight(){
        int r = (int) (Math.random() * 180);
        dirRight.add(r);
    }
}
