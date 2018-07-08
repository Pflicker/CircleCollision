package de.beaverstudios.cc.Box2D;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {

    public int x;
    public int y;
    public float vel;
    World b2dWorld;
    private Body b2dPlayer;

    public Player(int x, int y, World b2dWorld) {
        this.x = x;
        this.y = y;
        this.b2dWorld = b2dWorld;
        this.vel = 2f;

        //Create b2d object
        createPlayer();
    }

    private void createPlayer(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        System.out.println("create Player");
        // add it to the world
        b2dPlayer = b2dWorld.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        b2dPlayer.createFixture(shape, 0.0f);
        b2dPlayer.setLinearVelocity(1f,0f);
        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    public void ApplyMove(int deg){
        float vX;
        float vY;
        float b2dDeg;
        float deg2rad;

        deg2rad = (float) (Math.PI/180f);

        //b2dDeg  = (float) (deg)

        vX = (float) (vel*Math.sin(deg*deg2rad));
        vY = (float) (vel*Math.cos(deg*deg2rad));

        b2dPlayer.setTransform(b2dPlayer.getPosition(), (float) (90-deg)*deg2rad);
        b2dPlayer.setAngularVelocity(0);
        //System.out.println("body angle: " + b2dPlayer.getAngle()*180f/Math.PI);
        //System.out.println("vel " + vX + " " + vY + " " + " deg " + deg);
        b2dPlayer.setLinearVelocity(vX,vY);
    }

}
