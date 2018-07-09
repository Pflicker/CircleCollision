package de.beaverstudios.cc.Box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class B2dModel {
    public World b2dWorld;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public B2dModel(){
        b2dWorld = new World(new Vector2(0,0f), true);
    }


    // our game logic here
    public void logicStep(float delta){

        b2dWorld.step(delta , 3, 3);
    }
}