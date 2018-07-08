package de.beaverstudios.cc.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.beaverstudios.cc.CC;
import de.beaverstudios.cc.GameScreen;

public class UI extends Stage {

    public Viewport uiPort;
    public OrthographicCamera orthoCam;

    public Skin skin;

    public static Texture arrow;

    public static ArrowTable moveLeft;
    public static ArrowTable moveRight;

    private Label score, time;

    private TextButton play;

    public UI(Game game, SpriteBatch batch) {
        super(new FitViewport(CC.WIDTH, CC.HEIGHT, new OrthographicCamera()), batch);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        orthoCam = (OrthographicCamera) getCamera();
        uiPort = getViewport();

        arrow = new Texture(Gdx.files.internal("ui/pfeil_oben.png"));

        moveLeft = new ArrowTable(false, GameScreen.dirLeft);
        moveRight = new ArrowTable(true, GameScreen.dirRight);

        score = new Label("Score: " + GameScreen.score, skin);
        score.setPosition(100,Gdx.graphics.getHeight()-50);

        time = new Label("Time: " + GameScreen.time, skin);
        time.setPosition(300, Gdx.graphics.getHeight()-50);

        play = new TextButton("Play", skin);
        play.setSize(50,50);
        play.setPosition(Gdx.graphics.getWidth()-play.getWidth(),Gdx.graphics.getHeight()-play.getHeight());
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.play){
                    GameScreen.play = false;
                    play.setText("Pause");
                } else {
                    GameScreen.play = true;
                    play.setText("Play");
                }
            }
        });


        addActor(moveLeft);
        addActor(moveRight);
        addActor(time);
        addActor(score);
        addActor(play);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        score.setText("Score: " + GameScreen.score);
        time.setText("Time: " + (int) GameScreen.time);
    }
}
