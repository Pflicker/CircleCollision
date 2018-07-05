package de.beaverstudios.cc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI extends Stage {

    public Viewport uiPort;
    public OrthographicCamera orthoCam;

    public Skin skin;

    public static Texture arrow;

    public static ArrowTable moveLeft;
    public static ArrowTable moveRight;

    public UI(Game game, SpriteBatch batch) {
        super(new FitViewport(CC.WIDTH, CC.HEIGHT, new OrthographicCamera()), batch);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        orthoCam = (OrthographicCamera) getCamera();
        uiPort = getViewport();

        arrow = new Texture(Gdx.files.internal("ui/pfeil_oben.png"));

        moveLeft = new ArrowTable(false,GameScreen.dirLeft);
        moveRight = new ArrowTable(true, GameScreen.dirRight);

        addActor(moveLeft);
        addActor(moveRight);

    }
}
