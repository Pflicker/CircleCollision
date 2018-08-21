package de.beaverstudios.cc.Screens;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import net.dermetfan.gdx.graphics.g2d.Box2DPolygonSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import de.beaverstudios.cc.Box2D.B2dModel;
import de.beaverstudios.cc.Box2D.ListenerClass;
import de.beaverstudios.cc.CC;
import de.beaverstudios.cc.InputController;
import de.beaverstudios.cc.Universe;
import de.beaverstudios.cc.Utils;
import de.beaverstudios.cc.ui.UI;

import static de.beaverstudios.cc.Utils.sigmoid;
import static de.beaverstudios.cc.Utils.worldToScreen;

public class GameScreen implements Screen {

    public Game game;

    public SpriteBatch batch;
    public PolygonSpriteBatch polyBatch;

    public UI ui;

    public InputMultiplexer inputMultiplexer;
    public InputController ip;

    public static Array<Integer> dirLeft;
    public static Array<Integer> dirRight;

    public Vector2 playerPos;

    public OrthographicCamera cam;
    public B2dModel model;
    public Box2DDebugRenderer debugRenderer;
    public ContactListener contactListener;

    public static float time;
    public static float score;

    public static boolean play = true;

    public Texture player;
    public Sprite spPlayer;
    public Texture background;
    public Sprite farStars;
    public Sprite planet1;

    public static int vpW;
    public static int vpH;

    public static Universe universe;

    public GameScreen(CC cc) {
        //TODO Ads einfügen
        //TODO Safe Score on Device
        //TODO Particle Effect Player
        //TODO Spieler Leben einfügen
        //TODO Objekte / Bonuspunkte Extr Leben (Schild ? wie +1 Leben)
        //TODO Polygons für Asteroiden
        //TODO Polygons übermalen / Bilder für die Polygons malen
        //TODO UI verbessern

        this.game = cc;
        this.batch = cc.gameBatch;

        vpH = 24;
        vpW = 32;

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

        contactListener = new ListenerClass(model.b2dWorld);
        model.b2dWorld.setContactListener(contactListener);

        //Create Wolrd
        universe = Universe.getInstance(model.b2dWorld);

        score = 0;
        time = 0;

        background = new Texture(Gdx.files.internal("Hintergrund.png")); //TODO Größe ändern
        farStars = new Sprite(new Texture(Gdx.files.internal("farstars.png"))); // TODO Größe ändern
        planet1 = new Sprite(new Texture(Gdx.files.internal("planet1.png"))); //TODO Größe ändern
        planet1.setY(400);
        planet1.setX(1400);
        planet1.setScale(0.3f);

        player = new Texture(Gdx.files.internal("player2.png"));
        spPlayer = new Sprite(player);
        spPlayer.scale(0.01f);
        }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        playerPos = new Vector2(worldToScreen(universe.player.b2dPlayer.getPosition().x,universe.player.b2dPlayer.getPosition().y));
        update(dt);

        Gdx.gl.glClearColor(1, 1, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(background,0,0);
        batch.draw(farStars,farStars.getX(),farStars.getY()); //TODO an Geschwindigkeit von Cam anpassen
        batch.draw(planet1,planet1.getX(),planet1.getY(),32,32); //TODO an Geschwindigkeit von Cam anpassen

        batch.draw(player,playerPos.x,playerPos.y,50,50,100,100,1,1,universe.player.rotation,1,1,player.getWidth(),player.getHeight(),false,false);
        //debugRenderer.render(model.b2dWorld, cam.combined);
        batch.end();

        polyBatch.begin();
        //Box2DPolygonSprite.draw(polyBatch, universe.world);
        for(Body aps : Universe.asteroids){
            ((Box2DPolygonSprite) aps.getUserData()).draw(polyBatch, aps);
        }
        polyBatch.end();

        ui.draw();
    }

    public void update(float dt){
        if(play) {
            time += dt;
            score += dt;
            universe.updateVCam();
            universe.CreateAsteroid();
            universe.deleteDeadAsteroids();
            universe.periodicBoundaries();
            advanceDifficulty(dt);
            updateScore(dt);
            model.logicStep(dt);

            float currentX = farStars.getX();
            farStars.setX(currentX - dt*20);
            currentX = planet1.getX();
            planet1.setX(currentX - dt*40);

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

    public void moveLeft() {
        universe.player.ApplyMove(dirLeft.get(0));
        dirLeft.removeIndex(0);
        createDirLeft();
    }

    public void moveRight() {
        universe.player.ApplyMove(dirRight.get(0));
        dirRight.removeIndex(0);
        createDirRight();
    }

    public void createDirLeft(){
        int rnd;

        if (dirLeft.size == 0){
            rnd = (int) (Math.random() * 180 + 180);
            dirLeft.add(rnd);
            return;
        }

        do{
            rnd = (int) (Math.random() * 180 + 180);
        }while(Math.abs(rnd-dirLeft.peek()) < 30);

        dirLeft.add(rnd);
    }

    public void createDirRight(){
        int rnd;

        if (dirRight.size == 0){
            rnd = (int) (Math.random() * 180);
            dirRight.add(rnd);
            return;
        }

        do{
            rnd = (int) (Math.random() * 180);
        } while(Math.abs(rnd-dirRight.peek()) < 30);

        dirRight.add(rnd);
    }

    public void updateScore(float dt){
        score += universe.vCam*dt;
        return;
    }

    public void advanceDifficulty(float dt){
        universe.dtAst = 1f + universe.dtAstInit* sigmoid(-time, universe.gameTimeNorm, 5);
        universe.v0    = universe.v0Init*sigmoid(time, universe.gameTimeNorm, -5);
        //System.out.println("v0 " + universe.v0 + "   dt " + universe.dtAst);
        return;
    }
}
