package de.beaverstudios.cc.Box2D;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import de.beaverstudios.cc.Universe;

public class Player {

    private static Player instance;
    public float vel;
    public float rotation;
    World b2dWorld;
    public Body b2dPlayer;
    public Universe universe;

    public Player(World b2dWorld) {
        this.b2dWorld = b2dWorld;
        rotation = 0;
        this.universe = Universe.getInstance(b2dWorld);
        this.vel = universe.v0;

        //Create b2d object
        createPlayer();
    }

    public static Player getInstance(World world){
        if(Player.instance == null){
            Player.instance = new Player(world);
        }
        return Player.instance;
    }


    private void createPlayer(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(1.f,1.f);

        System.out.println("create Player");
        // add it to the world
        b2dPlayer = b2dWorld.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,0.5f);

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
        Vector2 direction = new Vector2(0,0);
        deg2rad = (float) (Math.PI/180f);

        //b2dDeg  = (float) (deg)

        vX = (float) (vel*Math.sin(deg*deg2rad)) - universe.vCam;
        vY = (float) (vel*Math.cos(deg*deg2rad));

        rotation = (float) (90-deg);

        System.out.println("R: " + b2dPlayer.getPosition());

        b2dPlayer.setTransform(b2dPlayer.getPosition(), rotation*deg2rad);
        b2dPlayer.setAngularVelocity(0);
        //System.out.println("body angle: " + b2dPlayer.getAngle()*180f/Math.PI);
        System.out.println("vel " + vX + " " + vY + " " + " deg " + deg + " Vcam " + - Universe.getInstance(b2dWorld).vCam);
        b2dPlayer.setLinearVelocity(vX,vY);
        //b2dPlayer.applyLinearImpulse(direction,b2dPlayer.getPosition(),true);
    }

}
