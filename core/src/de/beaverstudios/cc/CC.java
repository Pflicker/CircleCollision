package de.beaverstudios.cc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.beaverstudios.cc.Screens.GameScreen;

public class CC extends Game {

	public static int WIDTH =1280;
	public static int HEIGHT = 720;
	public static SpriteBatch gameBatch;


	@Override
	public void create () {
		gameBatch = new SpriteBatch();
		setScreen(new GameScreen(this));
		super.render();
	}

	@Override
	public void render () {
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
	}
}
